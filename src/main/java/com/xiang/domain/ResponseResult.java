package com.xiang.domain;

import com.xiang.enums.AppHttpCodeEnum;
import lombok.Data;

@Data
public class ResponseResult <T>{
    private Integer code;
    private String msg;
    private T data;

    public ResponseResult(Integer code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public ResponseResult(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static <T> ResponseResult ok(T data) {
        return new ResponseResult(AppHttpCodeEnum.SUCCESS.getCode(),AppHttpCodeEnum.SUCCESS.getMsg(),data);
    }
    public static <T> ResponseResult ok() {
        return new ResponseResult(AppHttpCodeEnum.SUCCESS.getCode(),AppHttpCodeEnum.SUCCESS.getMsg());
    }

    public static <T> ResponseResult error(int code,String msg) {
        return new ResponseResult(code,msg);
    }

    public static ResponseResult error(AppHttpCodeEnum appHttpCodeEnum) {
        return new ResponseResult(appHttpCodeEnum.getCode(),appHttpCodeEnum.getMsg());
    }
}
