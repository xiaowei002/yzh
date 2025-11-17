package com.gsly.yzh.controller;

import com.gsly.yzh.annotation.RolePermission;
import com.gsly.yzh.service.TaskAssignService;
import com.gsly.yzh.utils.FileOperation;
import io.minio.GetObjectArgs;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.zip.ZipOutputStream;

@RestController
@RequestMapping("/file")
public class FileController {


    @Resource
    private TaskAssignService taskAssignService;

    @RolePermission
    @GetMapping("/task/{taskId}/downloadAll")
    public void downloadAll(@PathVariable Long taskId, HttpServletResponse response) throws Exception {
        taskAssignService.downloadAll(taskId, response);
    }
}
