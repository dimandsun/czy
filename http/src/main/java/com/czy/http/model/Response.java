package com.czy.http.model;

import com.czy.http.enums.MIMEEnum;
import com.czy.http.model.MIME;
import com.czy.http.model.QuestScheme;
import com.czy.http.model.ServerInfo;
import com.czy.util.enums.QuestMethodEnum;
import com.czy.util.model.StringMap;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.Locale;
import java.util.Map;

/**
 * @author chenzy
 * @date 2020-07-29
 */
public class Response{
    public Response(ServerInfo serverInfo) {
        this.serverInfo = serverInfo;
    }

    /**********************************第一行*******************************************************/
    //GET
    private QuestMethodEnum questMethodEnum;
    //nelson
    private String path;
    //get请求取url的?号后值a=123
    private StringMap<String[]> parMap;
    //HTTP/1.1 new QuestScheme(QuestScheme.HTTP,1.1);
    private QuestScheme questScheme;
    /**********************************请求头信息列表，每一行都是key: value*******************************************************/
    private StringMap<String> headerMap;

    private ServerInfo serverInfo;
    /*Accept：浏览器可接受的MIME类型。*/
//    Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9
    private Map<MIMEEnum, MIME> mimeMap;

    /**********************************空行，用于与请求体分开*******************************************************/


    /**********************************请求体*******************************************************/
    private String body;

}
