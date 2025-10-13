package com.gsly.yzh.controller;

import com.gsly.yzh.utils.FileOperation;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/file")
public class FileController {

    @Resource
    private FileOperation fileOperation;

    @GetMapping("/download")
    public void download(String fileName, HttpServletResponse response) {
        fileOperation.download(fileName, response);
    }

    @PostMapping("/upload")
    public void upload(MultipartFile file) {
        fileOperation.upload(file);
    }
}
