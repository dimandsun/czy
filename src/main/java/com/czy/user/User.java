package com.czy.user;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author chenzy
 * @since 2020-04-24
 * @description
 */
public class User {
    private Integer id;
    private String code;
    private String name;
    /*加密后的密码*/
    private String ps;
    /*原密码*/
    @JsonProperty("original_ps")
    private String originalPS;
    private String email;
    private String mobile;

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

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}
