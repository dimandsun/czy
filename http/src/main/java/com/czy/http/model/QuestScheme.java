package com.czy.http.model;

import com.czy.util.model.StringMap;

/**
 * @author chenzy
 * @date 2020-07-29
 * 发出请求的方案的名称，例如http、https或ftp。
 */
public record QuestScheme(String scheme, double version) {
    private static final StringMap<QuestScheme> schemeMap=new StringMap<>();
    public final static String HTTP="HTTP";
    public final static String HTTPS="HTTPS";
    public final static String FTP="FTP";

    @Override
    public String toString() {
        return scheme+"/"+version;
    }

    //HTTP/1.1
    public static QuestScheme get(String msg){
        if (!msg.contains("/")){
            return null;
        }
        var result =schemeMap.get(msg);
        if (result==null){
            var temp=msg.split("/");
            result=new QuestScheme(temp[0],Double.valueOf(temp[1]));
            schemeMap.add(msg,result);
        }
        return result;
    }

}
