package com.gsly.yzh.interceptor;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.gsly.yzh.annotation.RolePermission;
import com.gsly.yzh.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.imageio.plugins.tiff.ExifParentTIFFTagSet;

/**
 * @author 魏国伟
 * jwt校验拦截器
 */
@Slf4j
@Component
public class JwtInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //获取token
        String authorization = request.getHeader("Authorization");
        //不合法的token
        if (authorization != null && !authorization.startsWith("Bearer ")) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("请登录后再访问！");
            return false;
        }
        //截取token
        String token = authorization.substring(7);
        Claims claims;
        //解析
        try {
            claims = JwtUtils.parseToken(token);
            request.setAttribute("username", claims.get("username"));
            request.setAttribute("role", claims.get("role"));
        } catch (ExpiredJwtException e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("登录已过期");
            return false;
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("token 无效");
            return false;
        }

        //权限校验
        if (handler instanceof HandlerMethod handlerMethod) {
            RolePermission methodAnnotation = handlerMethod.getMethodAnnotation(RolePermission.class);
            //标注了注解
            if (methodAnnotation != null) {
                int value = methodAnnotation.value();
                int role = (int) claims.get("role");
                if (value != role) {
                    response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                    response.getWriter().write("无访问权限");
                    return false;
                }
            }
            return false;
        }
        return true;
    }
}
