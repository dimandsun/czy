package com.czy.car_client;

import com.czy.core.model.ProjectInfo;
import com.czy.util.StringUtil;

import java.io.IOException;
import java.net.Socket;
import java.util.Map;

/**
 * @author chenzy
 * @since 2020-06-17
 */
public class CarClientInfo extends ProjectInfo {
    private static CarClientInfo instance=new CarClientInfo();
    private CarClientInfo() {
    }

    public static CarClientInfo getInstance() {
        return instance;
    }

    @Override
    public CarClientInfo init(String moduleDir) {
        Map<String, Object> profileMap = initPro(moduleDir);
        if (profileMap==null||profileMap.isEmpty()){
            return this;
        }
        Integer port = StringUtil.getInt(profileMap.get("port"),80);
        String host = StringUtil.getStr(profileMap.get("host"),"127.0.0.1");
        setPort(port);
        setHost(host);
        return this;
    }

    //服务器端口
    private Integer port;
    //服务器ip
    private String host;
    //与服务端通信
    private Socket socket;
    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public Integer getPort() {
        return port;
    }
    public void setPort(Integer port) {
        this.port = port;
    }

    public static void setInstance(CarClientInfo instance) {
        CarClientInfo.instance = instance;
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public CarClientInfo createSocket() {
        try {
            setSocket(new Socket(getHost(), getPort()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return this;
    }
}
