package com.czy.http.model;

import com.czy.http.ApplicationContext;
import com.czy.http.enums.MIMEEnum;
import com.czy.util.enums.QuestMethodEnum;
import com.czy.util.model.StringMap;
import com.czy.util.text.Line;

import java.io.File;
import java.util.Map;

/**
 * @author chenzy
 * @date 2020-07-29
 */
public class Response{


    public Response(ServletInfo servletInfo) {
        this.servletInfo = servletInfo;
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


    /*Accept：浏览器可接受的MIME类型。*/
//    Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9
    private Map<MIMEEnum, MIME> mimeMap;

    /**********************************空行，用于与请求体分开*******************************************************/


    /**********************************请求体*******************************************************/
    private StringBuilder body=new StringBuilder();
    /********************************************其他信息**************************************************/
    private ServletInfo servletInfo;
    private ApplicationContext applicationContext;
    private File file;//返回的文件

    public ApplicationContext getApplicationContext() {
        return applicationContext;
    }
    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }


    public StringBuilder append(Object data){
        return body.append(data);
    }
    public StringBuilder appendLine(Object data){
        return body.append(data+ Line.separator);
    }
    public String getBody() {
        return body.toString();
    }

    public File getFile() {
        return file;
    }
    public void setFile(File file) {
        this.file = file;
    }
}
