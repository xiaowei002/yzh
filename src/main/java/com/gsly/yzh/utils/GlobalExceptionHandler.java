package com.gsly.yzh.utils;

import com.gsly.yzh.enums.ResponseStatusEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
   

    /**
     * 全局异常处理
     * @param e
     * @return
     */
    @ResponseBody
    @ExceptionHandler(Exception.class)
    public ResponseVO<?> exceptionHandler(Exception e) {
        //业务异常（保留原始信息返回给前端）
        if (e instanceof BizException bizException) {
            log.warn("【业务异常】code={}, message={}", bizException.getCode(), bizException.getMessage());
            Integer code = bizException.getCode() != null
                    ? bizException.getCode()
                    : ResponseStatusEnum.BAD_REQUEST.getCode();
            return ResponseVO.failure(code, bizException.getMessage());
        }

        //参数校验异常
        else if (e instanceof MethodArgumentNotValidException methodArgumentNotValidException) {
            Map<String, String> map = new HashMap<>();
            BindingResult result = methodArgumentNotValidException.getBindingResult();
            result.getFieldErrors().forEach(item -> map.put(item.getField(), item.getDefaultMessage()));
            log.error("【参数校验错误】{}", map, e);
            return ResponseVO.failure(ResponseStatusEnum.BAD_REQUEST, map);
        }

        //请求方法不支持
        else if (e instanceof HttpRequestMethodNotSupportedException) {
            log.error("【请求方法错误】", e);
            return ResponseVO.failure(ResponseStatusEnum.BAD_REQUEST.getCode(), "请求方法不正确");
        }

        //缺少请求参数
        else if (e instanceof MissingServletRequestParameterException ex) {
            log.error("【请求参数缺失】", e);
            return ResponseVO.failure(ResponseStatusEnum.BAD_REQUEST.getCode(), "缺少参数：" + ex.getParameterName());
        }

        //参数类型错误
        else if (e instanceof MethodArgumentTypeMismatchException ex) {
            log.error("【请求参数类型错误】", e);
            return ResponseVO.failure(ResponseStatusEnum.BAD_REQUEST.getCode(), "参数类型错误：" + ex.getName());
        }

        //路径不存在
        else if (e instanceof NoHandlerFoundException ex) {
            log.error("【路径不存在】{}", ex.getRequestURL(), e);
            return ResponseVO.failure(ResponseStatusEnum.NOT_EXIST, "请求路径不存在: " + ex.getRequestURL());
        }

        //其他系统异常
        else {
            log.error("【系统异常】", e);
            return ResponseVO.failure(ResponseStatusEnum.SYSTEM_ERROR.getCode(), "系统繁忙，请稍后再试");
        }
    }

}
