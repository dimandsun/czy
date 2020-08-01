package com.czy.http.enums;

import com.czy.util.enums.IEnum;

/**
 * @author chenzy
 * @date 2020-08-01
 */
public enum ResponseCode implements IEnum<Integer> {
    Success(200, "OK"), NotFound(404, "Not Found");

    ResponseCode(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    private Integer code;
    private String msg;

    @Override
    public Integer getValue() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
