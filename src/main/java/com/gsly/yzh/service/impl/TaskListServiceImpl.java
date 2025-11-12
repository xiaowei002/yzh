package com.gsly.yzh.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gsly.yzh.domain.BaseEntity;
import com.gsly.yzh.domain.TaskListEntity;
import com.gsly.yzh.domain.dto.req.TaskListReqDto;
import com.gsly.yzh.domain.dto.req.TaskListSaveDto;
import com.gsly.yzh.domain.dto.req.TaskListUpdDto;
import com.gsly.yzh.mapper.TaskListMapper;
import com.gsly.yzh.service.TaskListService;
import com.gsly.yzh.utils.BizException;
import io.jsonwebtoken.lang.Assert;
import io.jsonwebtoken.lang.Collections;
import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * 任务管理
 */
@Service
public class TaskListServiceImpl extends ServiceImpl<TaskListMapper, TaskListEntity> implements TaskListService {

    //允许上传的文件类型
    public static final List<String> ALLOW_TYPE_LIST = Arrays.asList("pdf", "doc", "docx", "zip", "rar", "7z", "xls", "xlsx");
    //进行中
    public static final Integer UN_FINISHED = 1;

    @Resource
    private TaskListMapper taskListMapper;


    @Override
    public boolean addTaskList(TaskListSaveDto taskListSaveDto) {
        //判断是否为正常格式
        if (taskListSaveDto == null) {
            throw new BizException("任务不能为空");
        }
        //判断格式
        String[] split = taskListSaveDto.getType().split(",");
        for (String s : split) {
            if (!ALLOW_TYPE_LIST.contains(s)) {
                throw new BizException("允许上传的格式只能为文档、pdf、压缩包和表格");
            }
        }
        //校验截止时间不能小于当前时间
        if (taskListSaveDto.getDeadLine().isBefore(LocalDateTime.now())) {
            throw new BizException("截止时间不能小于当前时间！");
        }
        TaskListEntity taskListEntity = new TaskListEntity();
        //新创建的任务默认进行中
        taskListEntity.setStatus(1);
        //保存
        BeanUtils.copyProperties(taskListSaveDto, taskListEntity);
        return taskListMapper.insert(taskListEntity) > 0;
    }

    @Override
    public boolean updTaskList(TaskListUpdDto taskListUpdDto) {
        if (Objects.isNull(taskListUpdDto) || Objects.isNull(taskListUpdDto.getId())) {
            throw new BizException("修改时id为必传参数！");
        }
        //查询数据
        if (Objects.isNull(taskListMapper.selectById(taskListUpdDto.getId()))) {
            throw new BizException("无法查询到当前要修改的任务！");
        }
        TaskListEntity taskListEntity = new TaskListEntity();
        //拷贝数据并校验
        BeanUtils.copyProperties(taskListUpdDto, taskListEntity);
        //校验截止时间不能小于当前时间
        if (taskListEntity.getDeadLine().isBefore(LocalDateTime.now())) {
            throw new BizException("截止时间不能小于当前时间！");
        }
        return taskListMapper.updateById(taskListEntity) > 0;
    }

    @Override
    public boolean delTaskList(Long id) {
        //查询数据
        TaskListEntity taskListEntity = taskListMapper.selectById(id);
        if (Objects.isNull(taskListEntity)) {
            throw new BizException("无法查询到当前要修改的任务！");
        }
        if (UN_FINISHED.equals(taskListEntity.getStatus())) {
            throw new BizException("未完结的任务不能修改！");
        }
        return taskListMapper.deleteById(id) > 0;
    }

    @Override
    public IPage<TaskListEntity> getPageList(TaskListReqDto taskListReqDto) {
        LambdaQueryWrapper<TaskListEntity> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(Objects.nonNull(taskListReqDto.getStatus()), TaskListEntity::getStatus, taskListReqDto.getStatus());
        wrapper.ge(Objects.nonNull(taskListReqDto.getDeadlineStart()), TaskListEntity::getDeadLine, taskListReqDto.getDeadlineStart())
                .le(Objects.nonNull(taskListReqDto.getDeadlineEnd()), TaskListEntity::getDeadLine, taskListReqDto.getDeadlineEnd());
        return taskListMapper.selectPage(taskListReqDto, wrapper);
    }
}
