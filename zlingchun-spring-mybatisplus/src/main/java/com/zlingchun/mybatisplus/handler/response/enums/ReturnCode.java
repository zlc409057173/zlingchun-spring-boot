package com.zlingchun.mybatisplus.handler.response.enums;

public enum ReturnCode {
    /**
     * 操作成功
     **/
    RC100(100, "操作成功"),
    /**
     * 操作失败
     **/
    RC999(999, "操作失败"),
    /**
     * 服务限流
     **/
    RC200(200, "服务开启限流保护,请稍后再试!"),
    /**
     * 服务降级
     **/
    RC201(201, "服务开启降级保护,请稍后再试!"),
    /**
     * 热点参数限流
     **/
    RC202(202, "热点参数限流,请稍后再试!"),
    /**
     * 系统规则不满足
     **/
    RC203(203, "系统规则不满足要求,请稍后再试!"),
    /**
     * 授权规则不通过
     **/
    RC204(204, "授权规则不通过,请稍后再试!"),
    /**
     * access_denied
     **/
    RC403(403, "无访问权限,请联系管理员授予权限"),
    /**
     * access_denied
     **/
    RC401(401, "匿名用户访问无权限资源时的异常"),
    /**
     * 服务异常
     **/
    RC500(500, "系统异常，请稍后重试"),
    INVALID_TOKEN(2001, "访问令牌不合法"),
    TOKEN_EXPIRED(2002, "访问令牌已过期"),
    TOKEN_UNSUPPORTED(2003, "访问令牌不合法"),
    TOKEN_MALFORMED(2004, "访问令牌格式不合法"),
    TOKEN_SIGNATURE(2005, "访问令牌签名错误"),
    TOKEN_ILLEGALARGUMENT(2006, "没有权限访问该资源"),
    TOKEN_EMPTY(2007, "访问令牌为空"),
    CLIENT_AUTHENTICATION_FAILED(1001, "客户端认证失败"),
    USERNAME_OR_PASSWORD_ERROR(1002, "用户名或密码错误"),
    UNSUPPORTED_GRANT_TYPE(1003, "不支持的认证模式"),
    USER_NOT_EXIST(1004, "用户不存在")
    ;


    /**
     * 自定义状态码
     **/
    private final int code;
    /**
     * 自定义描述
     **/
    private final String message;

    ReturnCode(int code, String message) {
        this.code = code;
        this.message = message;
    }


    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
