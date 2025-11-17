package com.gsly.yzh.utils;

import com.gsly.yzh.domain.UserEntity;
import lombok.Data;

/**
 * 获取当前登录用户的工具类
 */
public class UserContext {

    private static final ThreadLocal<String> USER_HOLDER = new ThreadLocal<>();

    public static String getUser() {
        return USER_HOLDER.get();
    }

    public static void setUser(String username) {
        USER_HOLDER.set(username);
    }

    public static void clear() {
        USER_HOLDER.remove();
    }

}
