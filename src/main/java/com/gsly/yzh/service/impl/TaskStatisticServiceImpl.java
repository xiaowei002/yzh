package com.gsly.yzh.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gsly.yzh.domain.TaskStatisticEntity;
import com.gsly.yzh.domain.UserEntity;
import com.gsly.yzh.domain.dto.req.TaskStatisticReqDTO;
import com.gsly.yzh.domain.dto.res.RespTaskStatisticDTO;
import com.gsly.yzh.mapper.TaskStatisticMapper;
import com.gsly.yzh.mapper.UserMapper;
import com.gsly.yzh.service.TaskStatisticService;
import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class TaskStatisticServiceImpl extends ServiceImpl<TaskStatisticMapper, TaskStatisticEntity> implements TaskStatisticService {

    @Resource
    private TaskStatisticMapper taskStatisticMapper;

    @Resource
    private UserMapper userMapper;

    @Override
    public IPage<RespTaskStatisticDTO> getPageList(TaskStatisticReqDTO taskStatisticReqDTO) {
        LambdaQueryWrapper<TaskStatisticEntity> wrapper = Wrappers.lambdaQuery();
        wrapper.like(StringUtils.isNotBlank(taskStatisticReqDTO.getTaskName()), TaskStatisticEntity::getTaskName, taskStatisticReqDTO.getTaskName());
        IPage<TaskStatisticEntity> page = taskStatisticMapper.selectPage(taskStatisticReqDTO, wrapper);
        if (page.getTotal() == 0) {
            return new Page<>(page.getCurrent(), page.getSize(), page.getTotal());
        }
        List<RespTaskStatisticDTO> respTaskStatisticDTOS = page.getRecords().stream().map(taskStatisticEntity -> {
            RespTaskStatisticDTO respTaskStatisticDTO = new RespTaskStatisticDTO();
            BeanUtils.copyProperties(taskStatisticEntity, respTaskStatisticDTO);
            return respTaskStatisticDTO;
        }).toList();
        IPage<RespTaskStatisticDTO> resultPage = new Page<>(page.getCurrent(), page.getSize(), page.getTotal());
        resultPage.setRecords(respTaskStatisticDTOS);
        return resultPage;
    }
}
