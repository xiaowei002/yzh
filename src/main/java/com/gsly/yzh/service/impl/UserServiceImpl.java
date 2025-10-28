package com.gsly.yzh.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gsly.yzh.domain.UserEntity;
import com.gsly.yzh.mapper.UserMapper;
import com.gsly.yzh.service.UserService;
import com.gsly.yzh.utils.BizException;
import com.gsly.yzh.utils.JwtUtils;
import com.gsly.yzh.utils.PasswordEncrypt;
import io.jsonwebtoken.lang.Assert;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, UserEntity> implements UserService {

    @Resource
    private UserMapper userMapper;



    @Override
    public boolean insertUser(UserEntity user) {
        if (Strings.isBlank(user.getUsername())) {
            throw new BizException("用户名不能为空！");
        }
        if (Strings.isBlank(user.getPassword())) {
            throw new BizException("密码不能为空！");
        }
        if (Objects.isNull(user.getClassId())) {
            throw new BizException("需要选择班级！");
        }
        if (existsUser(user.getUsername())) {
            throw new BizException("当前用户名已存在！");
        }
        //判断新用户绑定的班级是否已经被绑定
        if (existsClassId(user.getClassId())) {
            throw new BizException("当前用户绑定的班级已经被绑定，一个用户只能绑定一个班级！");
        }
        user.setStatus(1); //启用
        String password = PasswordEncrypt.encryptPassword(user.getPassword());
        user.setPassword(password);
        return userMapper.insert(user) > 0;
    }

    /**
     * 登录
     * @param username 用户名
     * @param password 密码
     * @return
     */
    @Override
    public String login(String username, String password) {
        Assert.notNull(username, "登录时用户名不能为空！");
        Assert.notNull(password, "登录时密码不能为空！");
        //通过用户名查询用户
        LambdaQueryWrapper<UserEntity> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(UserEntity::getUsername, username);
        UserEntity userEntity = userMapper.selectOne(wrapper);
        if (Objects.isNull(userEntity) || !password.equals(userEntity.getPassword())) {
            throw new BizException("用户名或密码错误！");
        }
        return JwtUtils.generateToken(userEntity);
    }

    /**
     * 启用用户
     * @param id
     * @return
     */
    @Override
    public boolean enableUser(Long id) {
        UserEntity userEntity = userMapper.selectById(id);
        if (Objects.isNull(userEntity)) {
            throw new BizException("启用用户失败，当前传入的ID无法查询到用户！");
        }
        userEntity.setStatus(1);
        return userMapper.updateById(userEntity) > 0;
    }

    /**
     * 禁用用户
     * @param id
     * @return
     */
    @Override
    public boolean forbiddenUser(Long id) {
        UserEntity userEntity = userMapper.selectById(id);
        if (Objects.isNull(userEntity)) {
            throw new BizException("禁用用户失败，无法根据传递的ID查询到用户！");
        }
        userEntity.setStatus(0);
        return userMapper.updateById(userEntity) > 0;
    }

    @Override
    public boolean existsUser(String username) {
        if (Strings.isBlank(username)) {
            throw new BizException("用户名不合法！");
        }
        LambdaQueryWrapper<UserEntity> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(UserEntity::getUsername, username);
        return userMapper.exists(wrapper);
    }

    /**
     * 判断新用户绑定的班级是否已经被绑定，一个用户只能绑定一个班级
     * @param classId
     * @return
     */
    private boolean existsClassId(Long classId) {
        LambdaQueryWrapper<UserEntity> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(UserEntity::getClassId, classId);
        return userMapper.exists(wrapper);
    }
}
