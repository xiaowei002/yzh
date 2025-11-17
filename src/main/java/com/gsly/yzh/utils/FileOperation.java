package com.gsly.yzh.utils;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface FileOperation {
    /**
     * 文件上传
     * @param file
     * @return
     */
    String upload(MultipartFile file);

    void download(String fileName, HttpServletResponse response);

    void downloadBatch(List<String> fileNames, HttpServletResponse response);
}
