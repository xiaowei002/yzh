package com.gsly.yzh.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gsly.yzh.domain.TaskCompleteEntity;
import com.gsly.yzh.mapper.TaskCompleteMapper;
import com.gsly.yzh.service.TaskCompleteService;
import org.springframework.stereotype.Service;

@Service
public class TaskCompleteServiceImpl extends ServiceImpl<TaskCompleteMapper, TaskCompleteEntity> implements TaskCompleteService {
}
