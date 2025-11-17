package com.gsly.yzh.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") //所有接口
                .allowedOrigins("http://localhost:5173") //允许跨域的前端地址
        .allowedMethods("*") //所有方法
                .allowedHeaders("*") //所有请求头
        .allowCredentials(true);
    }
}
