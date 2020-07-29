package com.czy.util.http;

import com.czy.util.enums.IEnum;

/**
 * @author chenzy
 * @date 2020-07-29
 * 发出请求的方案的名称，例如http、https或ftp。
 */
public enum  QuestScheme implements IEnum<String> {
    HTTP,HTTPS,FTP;

    @Override
    public String getValue() {
        return name();
    }
}
