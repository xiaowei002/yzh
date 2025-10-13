package com.gsly.yzh.utils;

import com.gsly.yzh.config.MinioProperties;
import io.minio.*;
import jakarta.annotation.Resource;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * 文件上传下载工具类
 */
@Slf4j
@Service
public class FileOperationImpl implements FileOperation {

    @Resource
    private MinioClient minioClient;

    @Resource
    private MinioProperties minioProperties;

    @Override
    public String upload(MultipartFile file) {
        String fileName = "";
        try {
            //判断桶是否存在
            boolean found = minioClient.bucketExists(
                    BucketExistsArgs.builder()
                            .bucket(minioProperties.getBucketName())
                            .build()
            );
            //不存在创建
            if (!found) {
                minioClient.makeBucket(MakeBucketArgs.builder()
                        .bucket(minioProperties.getBucketName())
                        .build());
            }
            //设置文件名称
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String prefix = dtf.format(LocalDate.now());
            fileName = prefix + "_" + file.getOriginalFilename();
            //上传
            try (InputStream in = file.getInputStream()) {
                minioClient.putObject(
                        PutObjectArgs.builder()
                                .bucket(minioProperties.getBucketName())
                                .object(fileName)
                                .stream(in, file.getSize(), -1)
                                .contentType(file.getContentType())
                                .build()
                );
            }
        } catch (Exception e) {
            log.error("文件{}上传失败：{}", file.getOriginalFilename(), e.getMessage());
            throw new BizException("文件上传失败!");
        }
        return fileName;
    }

    @Override
    public void download(String fileName, HttpServletResponse response) {
        if (StringUtils.isBlank(fileName)) {
            throw new BizException("文件名不合法！");
        }
        String originalFileName = "";
        try {
            InputStream inputStream = minioClient.getObject(
                    GetObjectArgs.builder()
                            .bucket(minioProperties.getBucketName())
                            .object(fileName)
                            .build());
            ServletOutputStream outputStream = response.getOutputStream();
            // 设置响应类型（二进制流）
            response.setContentType("application/octet-stream");
            // 取出原始文件名（去掉时间前缀）
            originalFileName = fileName.substring(fileName.lastIndexOf('_') + 1);

            // 防止中文名乱码
            String encodedFileName = URLEncoder.encode(originalFileName, StandardCharsets.UTF_8);
            response.setHeader("Content-Disposition", "attachment; filename*=UTF-8''" + encodedFileName);

            // 将 InputStream 写到 Response OutputStream
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
            outputStream.flush();

        } catch (Exception e) {
            log.error("文件{}下载失败: {}", originalFileName, e.getMessage());
            throw new BizException("文件下载失败!");
        }
    }
}
