package com.czy.http.model;

import com.czy.http.enums.MIMEEnum;
import com.czy.http.model.MIME;
import com.czy.http.model.ServerInfo;
import com.czy.util.enums.QuestMethodEnum;
import com.czy.http.model.QuestScheme;
import com.czy.util.list.EnumerationFactory;
import com.czy.util.model.StringMap;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.security.Principal;
import java.util.*;

/**
 * @author chenzy
 * @date 2020-07-29
GET /nelson?a=123 HTTP/1.1
Host: 127.0.0.1:9090
Connection: keep-alive
Cache-Control: max-age=0
Upgrade-Insecure-Requests: 1
User-Agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/83.0.4103.97 Safari/537.36
Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*、*;q=0.8,application/signed-exchange;v=b3;q=0.9
        Sec-Fetch-Site: none
        Sec-Fetch-Mode: navigate
        Sec-Fetch-User: ?1
        Sec-Fetch-Dest: document
        Accept-Encoding: gzip, deflate, br
        Accept-Language: zh-CN,zh;q=0.9,et;q=0.8
 */
public class Request{
    /**********************************第一行*******************************************************/
    //GET
    private QuestMethodEnum questMethodEnum;
    //nelson
    private String route;
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


    /**********************************其他信息*****************************************************/

    public String getRoute() {
        return route;
    }

    public void setRoute(String route) {
        this.route = route;
    }


    public String getHeader(String name) {
        return headerMap.get(name);
    }


    /**
     * 以形式返回指定请求标头的值int。
     *
     * @param name
     * @return
     */
    public int getIntHeader(String name) {
        return Integer.valueOf(headerMap.get(name));
    }

    /**
     * 返回发出此请求的HTTP方法的名称，例如GET，POST或PUT。
     *
     * @return
     */
    public String getMethod() {
        return questMethodEnum.getMsg();
    }

    /**
     * 以字符串的形式返回请求参数的值，如果该参数不存在，则返回null。
     * @param name
     * @return
     */
    public String getParameter(String name) {
        var temp = parMap.get(name);
        return temp == null ? null : temp[0];
    }
    public String[] getParameterValues(String name) {
        return parMap.get(name);
    }
    public Map<String, String[]> getParameterMap() {
        return parMap;
    }

    /**
     * 返回用于发出此请求的方案的名称，例如http、https或ftp。
     * @return
     */
    public String getScheme() {
        return questScheme.scheme();
    }

    public BufferedReader getReader() throws IOException {
        return null;
    }
}