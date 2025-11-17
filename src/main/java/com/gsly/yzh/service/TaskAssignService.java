package com.gsly.yzh.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gsly.yzh.domain.TaskAssignEntity;
import com.gsly.yzh.domain.dto.req.TaskAssignSaveDTO;
import jakarta.servlet.http.HttpServletResponse;

public interface TaskAssignService extends IService<TaskAssignEntity> {

    boolean completeTask(TaskAssignSaveDTO taskAssignSaveDTO);

    void downloadAll(Long taskId, HttpServletResponse response);
}
