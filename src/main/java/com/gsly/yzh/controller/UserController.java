package com.gsly.yzh.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
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
    public ResponseVO<IPage<UserEntity>> getUserPageList(@RequestBody UserReqDTO userReqDTO){
        IPage<UserEntity> userPageList = userService.getUserPageList(userReqDTO);
        return ResponseVO.success(userPageList);
    }

    /**
     * 根据id删除
     * @param id
     * @return
     */
    @DeleteMapping("/deleteById/{id}")
    public ResponseVO<Boolean> deleteById(@PathVariable("id") Long id){
        boolean b = userService.deleteUserById(id);
        return ResponseVO.success(b);
    }
}
/**
 *  任务表                       任务完成表           任务统计表
 *  id                          id                 id
 *  任务名称                     任务id              任务id
 *  任务截止时间                  完成人（班长）       未完成人员
 *  任务上传格式（PDF，PPT,EXCEL）完成时间
 *  任务下发人员（班长）
 *
 */
