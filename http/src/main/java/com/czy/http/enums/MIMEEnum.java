package com.czy.http.enums;

import com.czy.util.enums.IEnum;

/**
 * @author chenzy
 * @date 2020-07-30
 */
public enum MIMEEnum implements IEnum<String> {
    Text("text/html"),Xhtml("application/xhtml+xml"),Xml("application/xml")
    ,Webp("image/webp"),Apng("image/apng"),All("*/*"),SignedExchange("application/signed-exchange")

    ;

    MIMEEnum(String msg) {
        this.msg = msg;
    }

    private String msg;
    @Override
    public String getValue() {
        return null;
    }
}
