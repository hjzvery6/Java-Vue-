package com.example.exception;

import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;

import com.example.common.Result; // 自定义的用于封装响应结果的类

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpServletRequest;

// 全局异常处理的类
@ControllerAdvice(basePackages="com.example.controller") // 指定该类是一个全局异常处理器，作用范围为com.example.controller，可以捕获这些控制器抛出的所有异常
public class GlobalExceptionHandler {
    // 日志记录异常信息
    private static final Log log = LogFactory.get();

    // 统一异常处理@ExceptionHandler,主要用于Exception
    @ExceptionHandler(Exception.class) // 捕获Exception的所有异常
    @ResponseBody // 确保返回的内容作为HTTP的响应体（json串）
    public Result error(HttpServletRequest request, Exception e){
        log.error("异常信息：",e);
        return Result.error(); // 错误响应
    }

    // 统一异常处理@ExceptionHandler,用于CustomException
    @ExceptionHandler(CustomException.class) // 捕获特定的CustomException异常
    @ResponseBody // 确保返回的内容直接作为HTTP响应体返回json串
    public Result customError(HttpServletRequest request, CustomException e){
        return Result.error(e.getCode(), e.getMsg());
    }

}
