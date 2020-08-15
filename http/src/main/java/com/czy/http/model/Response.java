package com.czy.http.model;

import com.czy.http.ApplicationContext;
import com.czy.http.Server;
import com.czy.http.enums.MIMEEnum;
import com.czy.http.enums.ResponseCode;
import com.czy.util.model.StringMap;
import com.czy.util.text.Line;
import com.czy.util.time.TimeUtil;

import java.io.File;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

/**
 * @author chenzy
 * @date 2020-07-29
 */
public class Response {
    public Response(ServletInfo servletInfo) {
        this.servletInfo = servletInfo;
        setApplicationContext(ApplicationContext.instance());
    }

    /**********************************第一行*******************************************************/
    //HTTP/1.1
    private QuestScheme questScheme;
    private ResponseCode responseCode;
    /**********************************请求头信息列表，每一行都是key: value*******************************************************/
    private StringMap<String> headerMap=new StringMap<>();
    /*Accept：浏览器可接受的MIME类型。*/
//    Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9
    private Map<MIMEEnum, MIME> mimeMap=new HashMap<>();

    /**********************************空行，用于与请求体分开*******************************************************/


    /**********************************请求体*******************************************************/
    private StringBuilder body = new StringBuilder();
    /********************************************其他信息**************************************************/
    private ServletInfo servletInfo;
    private ApplicationContext applicationContext;
    private File file;//返回的文件
    private StringBuilder result = new StringBuilder();//最后写入输出流的数据
    private Charset charSet;

    public ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    private void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }


    public StringBuilder append(Object data) {
        return body.append(data);
    }

    public StringBuilder appendLine(Object data) {
        return body.append(data + Line.separator);
    }

    public ResponseCode getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(ResponseCode responseCode) {
        this.responseCode = responseCode;
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

    public QuestScheme getQuestScheme() {
        return questScheme;
    }

    public void setQuestScheme(QuestScheme questScheme) {
        this.questScheme = questScheme;
    }

    public void beforeReturn() {
        if (responseCode==null){
            setResponseCode(ResponseCode.Success);
        }
        result.append(getQuestScheme()).append(Server.BANK).append(getResponseCode().getValue()).append(Server.BANK).append(getResponseCode().getMsg()).append(Server.CRLF);
        headerMap.add("Server", "czy Server/0.0.1")
                .add("Date", TimeUtil.nowStr());
        if (getContentType() == null) {
            setContentType("text/html;charset=" + getCharSet());
        }
        headerMap.add("Content-Length", body.toString().getBytes().length + "");
        headerMap.forEach((key, value) -> {
            result.append(key).append(":").append(value).append(Server.CRLF);
        });
        result.append("").append(Server.CRLF)
                .append(body.toString());
    }

    public String getResult() {
        return result.toString();
    }

    public void setContentType(String contentType) {
        headerMap.add("Content-type", contentType);
    }

    public String getContentType() {
        return headerMap.get("Content-type");
    }

    public void setCharSet(Charset charSet) {
        this.charSet = charSet;
    }

    public Charset getCharSet() {
        if (charSet == null) {
            setCharSet(ServerInfo.instance().getCharset());
        }
        return charSet;
    }

}
