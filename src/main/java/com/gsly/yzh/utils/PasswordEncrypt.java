package com.gsly.yzh.utils;

import org.springframework.util.DigestUtils;

/**
 * @author 魏国伟
 * 密码加密算法
 */

public class PasswordEncrypt {

    /**
     * 密码加密
     * @param password 登录时传递的密码
     * @return 加密后的密码
     */
    public static String encryptPassword(String password) {
        return DigestUtils.md5DigestAsHex(password.getBytes()).toLowerCase();
    }

    /**
     * 密码比对
     * @param password 传入的密码
     * @param encryptedPassword 数据库加密保存的密码
     * @return 是否匹配
     */
    public static boolean matches(String password, String encryptedPassword) {
        return DigestUtils.md5DigestAsHex(password.getBytes()).toLowerCase().equals(encryptedPassword);
    }

}
