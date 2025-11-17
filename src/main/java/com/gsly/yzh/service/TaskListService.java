package com.gsly.yzh.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gsly.yzh.domain.TaskListEntity;
import com.gsly.yzh.domain.dto.req.TaskListReqDTO;
import com.gsly.yzh.domain.dto.req.TaskListSaveDTO;
import com.gsly.yzh.domain.dto.req.TaskListUpdDTO;

public interface TaskListService extends IService<TaskListEntity> {


    boolean addTaskList(TaskListSaveDTO taskListSaveDto);

    boolean updTaskList(TaskListUpdDTO taskListUpdDto);

    boolean delTaskList(Long id);

    IPage<TaskListEntity> getPageList(TaskListReqDTO taskListReqDto);

}
