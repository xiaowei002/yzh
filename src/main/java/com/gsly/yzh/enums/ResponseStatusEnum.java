package com.gsly.yzh.enums;

import lombok.Data;
import lombok.Getter;
import org.springframework.data.relational.core.sql.In;
@Getter
public enum ResponseStatusEnum {

    SUCCESS("操作成功",200),
    SYSTEM_ERROR("服务器错误", 500),
    BAD_REQUEST("错误请求", 400),
    NOT_EXIST("请求地址不存在", 404);


    private final String msg;
    private final Integer code;

    ResponseStatusEnum(String msg, Integer code) {
        this.msg = msg;
        this.code = code;
    }
    public String getMessage() {
        return msg;
    }
    public Integer getCode() {
        return code;
    }

}
