package com.xiang.enums;

public enum AppHttpCodeEnum {
    SUCCESS(200,"操作成功"),
    NEED_LOGIN(401,"需要登录后操作"),
    SYSTEM_ERROR(500,"出现错误"),
    NEED_USERNAME(402, "需要用户名"),
    NEED_PASSWORD(403, "需要密码"),
    NEED_PHONENUMBER(405, "需要电话号码"),
    USERNAME_EXIST(406, "用户名重复"),
    PHONENUMBER_EXIST(407, "电话号重复"),
    LOGIN_ERROR(408, "用户名或密码错误"),
    NO_SUCH_USER(409, "没有此用户"),
    RESUME_EXIST(410, "该用户履历已存在"),
    NO_SUCH_RESUME(411, "履历不存在");

    int code;
    String msg;
    AppHttpCodeEnum(int code, String errorMessage) {
        this.code = code;
        this.msg = errorMessage;
    }
    public int getCode() {
        return code;
    }
    public String getMsg() {
        return msg;
    }
}