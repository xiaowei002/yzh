package com.gsly.yzh.interceptor;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.gsly.yzh.utils.JwtUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * @author 魏国伟
 * jwt校验拦截器
 */
@Slf4j
public class JwtInterceptor implements HandlerInterceptor {

    /**
     * 校验token
     * @param request 请求
     * @param response 响应
     * @param handler 处理
     * @return 是否放行
     * @throws Exception 异常处理
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        String token = request.getHeader("token");
//        if (StringUtils.isEmpty(token)) {
//            throw new RuntimeException("token must not be empty");
//        }
//        //校验token
//        try {
//            JwtUtils.checkToken(token);
//        } catch ()

        return HandlerInterceptor.super.preHandle(request, response, handler);
    }
}
