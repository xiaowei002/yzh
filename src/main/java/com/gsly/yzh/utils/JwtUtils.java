package com.gsly.yzh.utils;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.gsly.yzh.domain.UserEntity;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.time.LocalDateTime;
import java.time.temporal.TemporalUnit;
import java.util.Base64;
import java.util.Date;

public class JwtUtils {
    //
    private static final Key KEY = Keys.hmacShaKeyFor("aVerySecretKeyForThisProject123456".getBytes());
    //过期时间
    private static final long EXPIRATION_TIME = 1000 * 60 * 60 * 24; // 1天

    /**
     * 生成token
     * @param user
     * @return
     */
    public static String generateToken(UserEntity user) {
        return Jwts.builder()
                .setSubject(String.valueOf(user.getId()))
                .claim("role", user.getRole())
                .setIssuedAt(new Date()) // 签发时间
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME)) // 过期时间
                .signWith(KEY) // 签名密钥
                .compact();
    }

    /**
     * 解析token
     * @param token
     * @return
     */
    public static Claims parseToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(KEY)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
