package com.czy.user.model;

import com.czy.util.json.JsonUtil;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author chenzy
 * @description
 * @since 2020-04-29
 */
public class User {
    private Integer id;
    private String code;
    private String name;
    private String ps;//'加密后的密码'
    @JsonProperty("original_ps")
    private String originalPS;//'原密码'
    private String email;
    private String mobile;

    @Override
    public String toString() {
        return JsonUtil.model2Str(this);
    }

    public User() {
    }

    public User(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPs() {
        return ps;
    }

    public void setPs(String ps) {
        this.ps = ps;
    }

    public String getOriginalPS() {
        return originalPS;
    }

    public void setOriginalPS(String originalPS) {
        this.originalPS = originalPS;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}
