package com.gsly.yzh.enums;

/**
 * @author 魏国伟
 * 接口返回枚举
 */

public enum ResponseEnum {

    SUCCESS(0,"请求处理成功！"),
    FAILED(1, "请求处理失败"),
    UNAUTHORIZED(401, "用户认证失败！"),
    FORBIDDEN(443, "权限不足！"),
    SERVER_ERROR(500, "服务器异常！");

    public final Integer code;
    public final String message;
    ResponseEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
