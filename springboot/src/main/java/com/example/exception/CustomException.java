package com.example.exception;

import com.example.common.ResultCode;

// 自定义异常类，继承自Java的RuntimeException，为项目提供统一的异常处理机制
public class CustomException extends RuntimeException {
    private String code; // 状态码
    private String msg; // 描述信息


    // 两个构造函数分别是不同的创建异常对象的方法
    public CustomException(ResultCode resultCode) {
        this.code = resultCode.code;
        this.msg = resultCode.msg;
    }
    public CustomException(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    // 下面是对状态码和描述信息的get和set方法
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
