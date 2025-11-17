package com.gsly.yzh.controller;

import com.gsly.yzh.domain.dto.req.TaskAssignSaveDTO;
import com.gsly.yzh.service.TaskAssignService;
import com.gsly.yzh.utils.ResponseVO;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 任务完成
 */
@RestController
@RequestMapping("/taskAssign")
public class TaskAssignController {

    @Resource
    private TaskAssignService taskAssignService;

    @PostMapping("/completeTask")
    public ResponseVO<Boolean>  completeTask(TaskAssignSaveDTO taskAssignSaveDTO) {
        return ResponseVO.success(taskAssignService.completeTask(taskAssignSaveDTO));
    }
}
