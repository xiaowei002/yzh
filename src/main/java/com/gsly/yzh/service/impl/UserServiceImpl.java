package com.gsly.yzh.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gsly.yzh.domain.UserEntity;
import com.gsly.yzh.mapper.UserMapper;
import com.gsly.yzh.service.UserService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, UserEntity> implements UserService {

    @Resource
    private UserMapper userMapper;

    @Override
    public boolean insertUser(UserEntity user) {
        return userMapper.insert(user) > 0;
    }
}
