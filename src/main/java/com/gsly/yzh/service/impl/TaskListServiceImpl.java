package com.gsly.yzh.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gsly.yzh.domain.*;
import com.gsly.yzh.domain.dto.req.TaskListReqDTO;
import com.gsly.yzh.domain.dto.req.TaskListSaveDTO;
import com.gsly.yzh.domain.dto.req.TaskListUpdDTO;
import com.gsly.yzh.mapper.TaskAssignMapper;
import com.gsly.yzh.mapper.TaskListMapper;
import com.gsly.yzh.mapper.TaskStatisticMapper;
import com.gsly.yzh.mapper.UserMapper;
import com.gsly.yzh.service.TaskAssignService;
import com.gsly.yzh.service.TaskListService;
import com.gsly.yzh.service.TaskStatisticService;
import com.gsly.yzh.utils.BizException;
import com.gsly.yzh.utils.UserContext;
import io.jsonwebtoken.lang.Assert;
import io.jsonwebtoken.lang.Collections;
import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 任务管理
 */
@Service
public class TaskListServiceImpl extends ServiceImpl<TaskListMapper, TaskListEntity> implements TaskListService {

    //允许上传的文件类型
    public static final List<String> ALLOW_TYPE_LIST = Arrays.asList("pdf", "doc", "docx", "zip", "rar", "7z", "xls", "xlsx");
    //未完成
    public static final Integer UN_FINISHED = 0;

    public static final Integer ADMIN = 1;

    @Resource
    private TaskListMapper taskListMapper;

    @Resource
    private TaskAssignService taskAssignService;

    @Resource
    private TaskAssignMapper taskAssignMapper;

    @Resource
    private UserMapper userMapper;

    @Resource
    private TaskStatisticService taskStatisticService;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean addTaskList(TaskListSaveDTO taskListSaveDto) {
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
        //保存
        BeanUtils.copyProperties(taskListSaveDto, taskListEntity);
        taskListMapper.insert(taskListEntity);
        //保存关系表
        List<TaskAssignEntity> taskAssignEntityList = taskListSaveDto.getUserIdList().stream().map(userId -> {
            TaskAssignEntity taskAssignEntity = new TaskAssignEntity();
            taskAssignEntity.setTaskId(taskListEntity.getId());
            taskAssignEntity.setUserId(userId);
            taskAssignEntity.setStatus(UN_FINISHED);
            return taskAssignEntity;
        }).toList();
        return taskAssignService.saveBatch(taskAssignEntityList);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updTaskList(TaskListUpdDTO taskListUpdDto) {
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
            throw new BizException("当前任务已经截止，无法修改！");
        }
        //如果任务类型被更改，那么之前上传的数据都应该清楚
        //todo 修改任务


        return taskListMapper.updateById(taskListEntity) > 0;
    }

    @Override
    public boolean delTaskList(Long id) {
        //查询数据
        TaskListEntity taskListEntity = taskListMapper.selectById(id);
        if (Objects.isNull(taskListEntity)) {
            throw new BizException("无法查询到当前要删除的任务！");
        }
        if (taskListEntity.getDeadLine().isAfter(LocalDateTime.now())) {
            throw new BizException("任务还未完成，不能被删除！");
        }
        return taskListMapper.deleteById(id) > 0;
    }

    @Override
    public IPage<TaskListEntity> getPageList(TaskListReqDTO taskListReqDto) {
        LambdaQueryWrapper<TaskListEntity> wrapper = Wrappers.lambdaQuery();
        //获取当前用户相关的任务，管理员的话获取所有的
        String userId = UserContext.getUser();
        //查询当前用户和和判断权限
        UserEntity userEntity = userMapper.selectById(userId);
        if (Objects.isNull(userEntity)) {
            throw new BizException("无法获取到当前登录用户，请联系管理员！");
        }
        if (Objects.nonNull(userEntity.getRole()) && !ADMIN.equals(userEntity.getRole())) {
            //获取当前用户相关的
            LambdaQueryWrapper<TaskAssignEntity> query = Wrappers.lambdaQuery();
            query.eq(TaskAssignEntity::getUserId, userId);
            List<TaskAssignEntity> taskAssignEntities = taskAssignMapper.selectList(query);
            if (CollectionUtils.isNotEmpty(taskAssignEntities)) {
                wrapper.in(TaskListEntity::getId, taskAssignEntities.stream().map(TaskAssignEntity::getTaskId).collect(Collectors.toList()));
            }
        }
        wrapper.like(StringUtils.isNotBlank(taskListReqDto.getName()), TaskListEntity::getName, taskListReqDto.getName());
        wrapper.ge(Objects.nonNull(taskListReqDto.getDeadlineStart()), TaskListEntity::getDeadLine, taskListReqDto.getDeadlineStart())
                .le(Objects.nonNull(taskListReqDto.getDeadlineEnd()), TaskListEntity::getDeadLine, taskListReqDto.getDeadlineEnd());
        return taskListMapper.selectPage(taskListReqDto, wrapper);
    }


    /**
     * 统计未提交
     */
    @Scheduled(cron = "0/1 * * * * *")
    public void schedule(){
        //查询所有未完成的任务以及该任务的截止时间
        LambdaQueryWrapper<TaskAssignEntity> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(TaskAssignEntity::getStatus, UN_FINISHED);
        List<TaskAssignEntity> taskAssignEntities = taskAssignMapper.selectList(wrapper);
        if (CollectionUtils.isNotEmpty(taskAssignEntities)) {
            //查询未完成任务的截止时间
            Map<Long, TaskAssignEntity> map = taskAssignEntities.stream().collect(Collectors.toMap(TaskAssignEntity::getTaskId, Function.identity()));
            List<TaskListEntity> taskListEntities = taskListMapper.selectByIds(map.keySet());
            List<TaskListEntity> taskListEntityList = taskListEntities.stream().filter(taskListEntity -> taskListEntity.getDeadLine().isBefore(LocalDateTime.now())).toList();
            List<TaskStatisticEntity> taskStatisticEntities = taskListEntityList.stream().map(taskListEntity -> {
                TaskStatisticEntity taskStatisticEntity = new TaskStatisticEntity();
                taskStatisticEntity.setTaskId(taskListEntity.getId());
                taskStatisticEntity.setUserId(map.get(taskListEntity.getId()).getUserId());
                taskStatisticEntity.setTaskName(taskListEntity.getName());
                taskStatisticEntity.setDeadLine(taskListEntity.getDeadLine());
                taskStatisticEntity.setUserName(userMapper.selectById(taskStatisticEntity.getUserId()).getUsername());
                return taskStatisticEntity;
            }).toList();
            taskStatisticService.saveBatch(taskStatisticEntities);
        }
    }
}
