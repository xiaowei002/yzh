package com.gsly.yzh.utils;

import lombok.Data;

/**
 * 业务异常
 */
@Data
public class BizException extends RuntimeException {
    /**
     * 错误码
     */
    private Integer code;
    /**
     * 错误信息
     */
    private String msg;

    public BizException(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public BizException() {}

    public BizException(String msg) {
        this.msg = msg;
    }
}
