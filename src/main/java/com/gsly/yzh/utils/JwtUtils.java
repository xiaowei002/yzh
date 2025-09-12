package com.gsly.yzh.utils;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.TemporalUnit;
import java.util.Base64;
import java.util.Date;

@Slf4j
@Component
public class JwtUtils {
    //盐
    private static final String SALT_KEY = "salt";
    //令牌有效期
    public static final long TOKEN_EXPIRATION_TIME = 864_000_000;
    //权限密钥
    public static final String AUTHORIZATION_HEADER = "Authorization";
    //256密钥
    private static final String SECRET_KEY = Base64.getEncoder().encodeToString(SALT_KEY.getBytes());

    /**
     * 创建token
     * @param userId 用户id
     * @param roleId 角色id
     * @return token
     */
    public static String createToken(String userId, String roleId) {
        Date validity = new Date((new Date()).getTime() + TOKEN_EXPIRATION_TIME);
        return Jwts.builder()
                //这个token的所有人
                .setSubject(userId)
                //签发主体
                .setIssuer("")
                //签发时间
                .setIssuedAt(new Date())
                .claim("role", roleId)
                .claim("user", userId)
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                //过期时间
                .setExpiration(validity)
                .compact();
    }

    /**
     * token解析
     * @param token 前端token
     * @return 解析后的用户
     */
    public static JwtUser checkToken(String token) {
        if (validateToken(token)) {
            Claims claims = Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
            //获取客户端
//            String audience = claims.getAudience();
            String userId = claims.get("user", String.class);
            String roleId = claims.get("role", String.class);
            JwtUser jwtUser = new JwtUser().setUserId(userId).setRoleId(roleId).setValid(true);
            log.info("token 有效：{}", userId);
            return jwtUser;
        }
        log.error("token无效：{}", token);
        return new JwtUser();
    }

    private static boolean validateToken(String token) {
        if (StringUtils.isEmpty(token)) {
            return false;
        }
        try {
            Claims claims = Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
            claims.getExpiration();
            return true;
        } catch (Exception e) {
            log.error("无效的token：{}", token);
        }
        return false;
    }
}
