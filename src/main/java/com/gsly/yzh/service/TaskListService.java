package com.gsly.yzh.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gsly.yzh.domain.TaskListEntity;
import com.gsly.yzh.domain.dto.req.TaskListReqDto;
import com.gsly.yzh.domain.dto.req.TaskListSaveDto;
import com.gsly.yzh.domain.dto.req.TaskListUpdDto;

public interface TaskListService extends IService<TaskListEntity> {


    boolean addTaskList(TaskListSaveDto taskListSaveDto);

    boolean updTaskList(TaskListUpdDto taskListUpdDto);

    boolean delTaskList(Long id);

    IPage<TaskListEntity> getPageList(TaskListReqDto taskListReqDto);

}
