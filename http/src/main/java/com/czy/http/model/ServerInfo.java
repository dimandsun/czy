package com.czy.http.model;

import com.czy.util.model.SettingFile;

import java.net.InetAddress;
import java.nio.charset.Charset;

/**
 * @author chenzy
 * @date 2020-07-28
 */
public class ServerInfo {
    private static ServerInfo instance=new ServerInfo();
    private ServerInfo(){}
    public static ServerInfo instance(){
        return instance;
    }
    private int port;
    private InetAddress address;
    private int timeout;
    /*编码*/
    private Charset charset;

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public InetAddress getAddress() {
        return address;
    }

    public void setAddress(InetAddress address) {
        this.address = address;
    }

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public Charset getCharset() {
        return charset;
    }

    public void setCharset(Charset charset) {
        this.charset = charset;
    }
}