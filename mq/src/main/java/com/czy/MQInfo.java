package com.czy;

import com.czy.util.FileUtil;
import com.czy.util.StringUtil;
import com.czy.util.model.StringMap;

import java.util.Map;

/**
 * @author chenzy
 * @since 2020-05-29
 */
public class MQInfo {
    private String host=null;
    private Integer port;
    private String userName;
    private String ps;

    private static MQInfo instance=new MQInfo();
    public static MQInfo getInstance() {
        return instance;
    }
    private MQInfo(){
        StringMap<Map<String,Object>> parMap= null;//FileUtil.readConfigFileByYML("mq.yml");
        var mqMap =parMap.get("mq");
        host= StringUtil.getStr(mqMap.get("host"),"127.0.0.1");
        port= StringUtil.getInt(mqMap.get("port"),5672);
        userName= StringUtil.getStr(mqMap.get("userName"),"guest");
        ps= StringUtil.getStr(mqMap.get("ps"),"guest");
    }

    public String getHost() {
        return host;
    }

    public Integer getPort() {
        return port;
    }

    public String getPS() {
        return userName;
    }

    public String getUserName() {
        return ps;
    }
}
