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
        initPro(moduleDir).ifPresent(map->{
            Integer port = StringUtil.getInt(map.get("port"),80);
            setPort(port);
        });

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
