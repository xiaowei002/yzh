package com.gsly.yzh.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.gsly.yzh.annotation.RolePermission;
import com.gsly.yzh.domain.UserEntity;
import com.gsly.yzh.domain.dto.req.UserReqDTO;
import com.gsly.yzh.service.UserService;
import com.gsly.yzh.utils.ResponseVO;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;

    /**
     * 注册
     * @param user 用户
     * @return 统一返回
     */
    @PostMapping("/register")
    public ResponseVO<Boolean> addUser(@RequestBody UserEntity user) {
        boolean b = userService.insertUser(user);
        return ResponseVO.success(b);
    }


    /**
     * 判断用户是否存在
     * @param username 用户名
     * @return 统一返回
     */
    @GetMapping("/exist")
    public ResponseVO<Boolean> exist(@RequestParam("username") String username){
        boolean b = userService.existsUser(username);
        return ResponseVO.success(b);
    }

    /**
     * 禁用用户
     * @param id 主键
     * @return 统一返回
     */
    @PutMapping("/forbidden/{id}")
    @RolePermission
    public ResponseVO<Boolean> forbidden(@PathVariable("id") Long id){
        boolean b = userService.forbiddenUser(id);
        return ResponseVO.success(b);
    }

    /**
     * 启用用户
     * @param id 主键
     * @return 统一返回
     */
    @PutMapping("/enable/{id}")
    @RolePermission
    public ResponseVO<Boolean> enable(@PathVariable("id") Long id){
        boolean b = userService.enableUser(id);
        return ResponseVO.success(b);
    }

    /**
     * 用户分页
     * @param userReqDTO
     * @return
     */
    @GetMapping("/getUserPageList")
    @RolePermission
    public ResponseVO<IPage<UserEntity>> getUserPageList(UserReqDTO userReqDTO){
        IPage<UserEntity> userPageList = userService.getUserPageList(userReqDTO);
        return ResponseVO.success(userPageList);
    }
//
//
//    @GetMapping("/test")
//    public void test(){
//        userService.create();
//    }

    /**
     * 根据id删除
     * @param id
     * @return
     */
    @DeleteMapping("/deleteById/{id}")
    @RolePermission
    public ResponseVO<Boolean> deleteById(@PathVariable("id") Long id){
        boolean b = userService.deleteUserById(id);
        return ResponseVO.success(b);
    }

    /**
     * 更新用户
     * @param user 用户信息
     * @return 统一返回
     */
    @PutMapping("/update")
    @RolePermission
    public ResponseVO<Boolean> updateUser(@RequestBody UserEntity user) {
        boolean b = userService.updateById(user);
        return ResponseVO.success(b);
    }
}

