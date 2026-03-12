package com.gsly.yzh.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gsly.yzh.domain.TaskAssignEntity;
import com.gsly.yzh.domain.TaskListEntity;
import com.gsly.yzh.domain.dto.req.TaskAssignSaveDTO;
import com.gsly.yzh.mapper.TaskAssignMapper;
import com.gsly.yzh.mapper.TaskListMapper;
import com.gsly.yzh.service.TaskAssignService;
import com.gsly.yzh.utils.BizException;
import com.gsly.yzh.utils.FileOperation;
import com.gsly.yzh.utils.UserContext;
import io.minio.GetObjectArgs;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.compress.utils.IOUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Service
public class TaskAssignServiceImpl extends ServiceImpl<TaskAssignMapper, TaskAssignEntity> implements TaskAssignService {

    public static final Integer UN_FINISHED = 0;
    public static final Integer FINISHED = 1;

    @Resource
    private TaskAssignMapper taskAssignMapper;

    @Resource
    private FileOperation fileOperation;

    @Resource
    private TaskListMapper taskListMapper;


    @Override
    public void downloadAll(Long taskId, HttpServletResponse response){
        // 1. 根据任务查出所有文件名
        List<String> fileNames = listFileNamesByTaskId(taskId);
        fileOperation.downloadBatch(fileNames, response);
    }

    /**
     * 查询某个任务下完成的任务名
     * @param taskId
     * @return
     */
    private List<String> listFileNamesByTaskId(Long taskId) {
        LambdaQueryWrapper<TaskAssignEntity> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(TaskAssignEntity::getTaskId, taskId);
        wrapper.eq(TaskAssignEntity::getStatus, FINISHED);
        List<TaskAssignEntity> taskAssignEntities = taskAssignMapper.selectList(wrapper);
        if (CollectionUtils.isEmpty(taskAssignEntities)) {
            return new ArrayList<>();
        }
        return taskAssignEntities.stream().map(TaskAssignEntity::getFilePath).toList();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean completeTask(TaskAssignSaveDTO taskAssignSaveDTO) {
        if (Objects.isNull(taskAssignSaveDTO)) {
            throw new BizException("提交任务失败，参数传递错误！");
        }
        MultipartFile file = taskAssignSaveDTO.getFile();
        if (Objects.isNull(taskAssignSaveDTO.getTaskId()) || Objects.isNull(file)) {
            throw new BizException("请传递正确的任务id和文件！");
        }
        //查询当前任务是否已经超过截止时间
        TaskListEntity taskListEntity = taskListMapper.selectById(taskAssignSaveDTO.getTaskId());
        if (Objects.isNull(taskListEntity)) {
            throw new BizException("无法查询到当前任务id，请练习管理员！");
        }
        if (taskListEntity.getDeadLine().isBefore(LocalDateTime.now())) {
            throw new BizException("当前任务已经截止！无法提交！");
        }
        String[] type = taskListEntity.getType().split(",");
        List<String> canUploadList = Arrays.asList(type); //允许上传的类型
        //校验类型
        String originalFilename = file.getOriginalFilename();
        if (originalFilename == null || !originalFilename.contains(".")) {
             throw new BizException("上传的文件名不合法！");
        }
        // 获取后缀（不带点），例如 "pdf"
        String fileType = originalFilename.substring(originalFilename.lastIndexOf(".") + 1).toLowerCase();
        if (!canUploadList.contains(fileType)) {
            throw new BizException(String.format("提交失败，当前上传类型 [%s] 不支持", fileType));
        }
        //获取当前登录用户
        String userId = UserContext.getUser();
        //上传文件
        String url = fileOperation.upload(file);
        //查询数据
        LambdaQueryWrapper<TaskAssignEntity> query = Wrappers.lambdaQuery();
        query.eq(TaskAssignEntity::getTaskId, taskAssignSaveDTO.getTaskId());
        query.eq(TaskAssignEntity::getUserId, userId);
        query.eq(TaskAssignEntity::getStatus, 0);
        TaskAssignEntity taskAssignEntity = taskAssignMapper.selectOne(query);
        if (Objects.isNull(taskAssignEntity)) {
            throw new BizException("数据错误，请联系管理员！");
        }
        taskAssignEntity.setStatus(FINISHED);
        taskAssignEntity.setSubmitTime(LocalDateTime.now());
        taskAssignEntity.setFilePath(url);
        return taskAssignMapper.updateById(taskAssignEntity) > 0;
    }
}
