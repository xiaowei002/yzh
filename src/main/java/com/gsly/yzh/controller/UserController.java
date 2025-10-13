package com.gsly.yzh.controller;

import com.gsly.yzh.domain.UserEntity;
import com.gsly.yzh.service.UserService;
import com.gsly.yzh.utils.ResponseVO;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;

    @PostMapping("/addUser")
    public ResponseVO<Boolean> addUser(UserEntity user) {
        user.setUsername("yzh");
        user.setPassword("123456");
        user.setNickname("杨振海");
        user.setPhone("13312311231");
        user.setEmail("xxx@gmail.com");
        user.setStatus("1");
        user.setClassId(1L);
        boolean b = userService.insertUser(user);
        return ResponseVO.success(b);
    }
}
