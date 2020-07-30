package com.czy.http.model;

/**
 * @author chenzy
 * @date 2020-07-29
 * 发出请求的方案的名称，例如http、https或ftp。
 */
public record  QuestScheme(String scheme, double version) {
    public final static String HTTP="HTTP";
    public final static String HTTPS="HTTPS";
    public final static String FTP="FTP";
}
