package com.gsly.yzh.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gsly.yzh.domain.UserEntity;
import org.springframework.stereotype.Service;

public interface UserService extends IService<UserEntity> {
    boolean insertUser(UserEntity user);

}
