package com.gsly.yzh.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gsly.yzh.domain.UserEntity;
import com.gsly.yzh.domain.dto.req.UserReqDTO;
import org.springframework.stereotype.Service;

public interface UserService extends IService<UserEntity> {
    /**
     * 注册
     * @param user
     * @return
     */
    boolean insertUser(UserEntity user);

    /**
     * 判断用户是否存在
     * @param username
     * @return
     */
    boolean existsUser(String username);

    /**
     * 禁用用户
     * @param id
     * @return
     */
    boolean forbiddenUser(Long id);


//    void create();

    /**
     * 启用用户
     * @param id
     * @return
     */
    boolean enableUser(Long id);


    /**
     * 根据id删除用户
     * @param id
     * @return
     */
    boolean deleteUserById(Long id);

    /**
     * 登录
     * @param username 用户名
     * @param password 密码
     * @return token
     */
    String login(String username, String password);


    /**
     * 用户分页接口
     * @param userReqDTO
     * @return
     */
    IPage<UserEntity> getUserPageList(UserReqDTO  userReqDTO);

}
