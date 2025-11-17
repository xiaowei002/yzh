package com.gsly.yzh.domain.dto.req;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class TaskAssignSaveDTO {

    /**
     * 任务id
     */
    private String taskId;

    /**
     * 文件
     */
    private MultipartFile file;

}
