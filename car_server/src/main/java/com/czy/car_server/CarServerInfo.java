package com.czy.car_server;

import com.czy.core.model.ProjectInfo;
import com.czy.util.text.StringUtil;

import java.util.Map;

/**
 * @author chenzy
 * @since 2020-06-17
 */
public class CarServerInfo extends ProjectInfo {
    public CarServerInfo() {
    }

    @Override
    public CarServerInfo init(String moduleDir) {
        Map<String, Object> profileMap = initPro(moduleDir);
        if (profileMap==null||profileMap.isEmpty()){
            return this;
        }
        Integer port = StringUtil.getInt(profileMap.get("port"),80);
        setPort(port);
        return this;
    }

    //服务端口
    private Integer port;

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }
}
