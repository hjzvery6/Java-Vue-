package com.example.common;

// 用于封装API响应结果的类Result，用于统一返回给客户端的数据格式
public class Result {
    private String code; // 响应的状态码
    private String msg; // 响应的描述信息
    private Object data; // 响应的具体数据

    private Result(Object data) {
        this.data = data;
    }

    public Result() {
    }
// 下面带static的都是静态工厂方法
    // 成功的对象
    public static Result success() {
        Result tResult = new Result();
        tResult.setCode(ResultCode.SUCCESS.code);
        tResult.setMsg(ResultCode.SUCCESS.msg);
        return tResult;
    }
    // 成功的有数据对象
    public static Result success(Object data) {
        Result tResult = new Result (data);
        tResult.setCode(ResultCode.SUCCESS.code);
        tResult.setMsg(ResultCode.SUCCESS.msg);
        return tResult;
    }
    // 错误的对象
    public static Result error() {
        Result tResult = new Result();
        tResult.setCode(ResultCode.ERROR.code);
        tResult.setMsg(ResultCode.ERROR.msg);
        return tResult;
    }
    // 自定义错误的对象，允许传入自定义状态码和描述信息
    public static Result error(String code, String msg) {
        Result tResult = new Result();
        tResult.setCode(code);
        tResult.setMsg(msg);
        return tResult;
    }
    // 错误的对象，但是使用指定的ResultCode枚举常量来设置状态码和描述信息
    public static Result error(ResultCode resultCode) {
        Result tResult = new Result();
        tResult.setCode(resultCode.code);
        tResult.setMsg(resultCode.msg);
        return tResult;
    }

// 下面这些是对这个类对象的属性进行get和set操作
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

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
