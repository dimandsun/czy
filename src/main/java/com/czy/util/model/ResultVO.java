package com.czy.util.model;

import com.czy.enums.ResCodeEnum;
import com.czy.util.json.JsonUtil;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

/**
 * 请求处理后返回结果的封装对象
 * @author chenzy
 * @date 2019.12.16
 */
public class ResultVO<T> {
    @JsonProperty("Data")
    private T data;
    @JsonProperty("Code")
    private ResCodeEnum code;
    @JsonProperty("Serial")
    private Long serial;
    @JsonProperty("Message")
    private String message;


    @JsonIgnore
    public boolean isNotNormal() {
        return code.isNotNormal();
    }
    @JsonIgnore
    public boolean isNormal() {
        return code.isNormal();
    }


    @Override
    public String toString() {
        return JsonUtil.model2Str(this);
    }
    public ResultVO(ResultVO resultVO) {
        this.code=resultVO.code;
        this.message=resultVO.message;
    }
    public ResultVO() {
        this.code = ResCodeEnum.Normal;
        setSerial();
        this.message = ResCodeEnum.Normal.getMSG();
    }
    public ResultVO(ResCodeEnum code) {
        this.code = code;
        setSerial();
        this.message = code.getMSG();
    }
    public ResultVO(T data) {
        this.code = ResCodeEnum.Normal;
        setSerial();
        this.message = ResCodeEnum.Normal.getMSG();
        this.data = data;
    }
    public ResultVO(T data, String message) {
        this.code = ResCodeEnum.Normal;
        setSerial();
        this.message = message;
        this.data = data;
    }
    public ResultVO(ResCodeEnum code, T data) {
        this.code = code;
        setSerial();
        this.message = code.getMSG();
        this.data = data;
    }

    public ResultVO(ResCodeEnum code, String message) {
        this.code = code;
        setSerial();
        if (message==null){
            this.message = code.getMSG();
        }else {
            this.message = message;
        }
    }
    public ResultVO(ResCodeEnum code, T data, String message) {
        this.data = data;
        this.code = code;
        setSerial();
        this.message = message;
    }
    public ResultVO(ResCodeEnum code, T data, Long serial, String message) {
        this.data = data;
        this.code = code;
        this.serial = serial;
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public ResCodeEnum getCode() {
        return code;
    }

    public void setCode(ResCodeEnum code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getSerial() {
        return serial;
    }
    public void setSerial() {
        serial = new Date().getTime();
    }
    public void setSerial(Long serial) {
        this.serial = serial;
    }
}
