package com.guangyou.rareanimal.common.lang;

import lombok.Data;

import java.io.Serializable;

@Data
public class Result implements Serializable {

    /**
     * 200 success
     * 请求响应成功
     */
    public final static int SUCCESS = 200;
    /**
     * 400 Bad Request
     * 坏响应。由于包含语法错误，当前请求无法被服务器理解。
     */
    public final static int BAD_REQUEST = 400;
    /**
     * 401 Unauthorized
     * 当前请求需要用户验证
     */
    public final static int UNAUTHORIZED = 401;
    /**
     * 403 Forbidden
     * 服务器收到请求，但拒绝执行，这个请求也应该被重复提交。
     */
    public final static int FORBIDDEN = 403;
    /**
     * 404 Not Found
     * 请求失败，请求所希望得到的资源未被在服务器上发现。服务器不需要解释为什么请求失败
     */
    public final static int NOT_FOUND = 404;
    /**
     * 405 method_not_allowed
     * 请求行中指定的请求方法不能被用于请求相应的资源。
     */
    public final static int METHOD_NOT_ALLOWED = 405;
    /**
     * 408 request timeout
     * 请求超时，客户端可以随时再次提交这一请求而无需进行任何更改。
     */
    public final static int REQUEST_TIMEOUT = 408;


    private int code;
    private String msg;
    private Object data;

    public static Result succ(Object data) {
        return succ(SUCCESS, "执行成功", data);
    }

    public static Result succ(int code, String msg, Object data) {
        Result r = new Result();
        r.setCode(code);
        r.setMsg(msg);
        r.setData(data);
        return r;
    }

    public static Result fail(String msg) {
        return fail(BAD_REQUEST, msg, null);
    }

    public static Result fail(String msg, Object data) {
        return fail(BAD_REQUEST, msg, data);
    }

    public static Result fail(int code, String msg, Object data) {
        Result r = new Result();
        r.setCode(code);
        r.setMsg(msg);
        r.setData(data);
        return r;
    }
}
