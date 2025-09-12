package com.gsly.yzh.utils;

import com.gsly.yzh.enums.ResponseEnum;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;

@Data
@Slf4j
public class ResponseResult<T>  implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 返回状态码
     */
    private Integer code;
    /**
     * 返回信息
     */
    private String message;
    /**
     * 返回数据
     */
    private T data;


    public ResponseResult(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public ResponseResult(Integer code, T data) {
        this.code = code;
        this.data = data;
    }

    public ResponseResult(Integer code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public ResponseResult(ResponseEnum httpEnum, T data) {
        this.code = httpEnum.code;
        this.message = httpEnum.message;
        this.data = data;
    }


    /**
     * 成功
     * @return
     */
    public static ResponseResult<Void> success() {
        return new ResponseResult<Void>(ResponseEnum.SUCCESS, null);
    }



    public static <T> ResponseResult<Void> success(ResponseEnum responseEnum, T data) {
        if (responseEnum == null) {
            return success();
        }
        return  new ResponseResult<Void>(responseEnum.code, responseEnum.message);
    }

}
