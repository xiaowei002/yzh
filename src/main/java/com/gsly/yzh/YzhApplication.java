package com.gsly.yzh;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
@SpringBootApplication
@MapperScan(basePackages = "com.gsly.yzh.mapper")
public class YzhApplication {

    public static void main(String[] args) {
        SpringApplication.run(YzhApplication.class, args);
    }

}
