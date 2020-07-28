package com.czy.httpcontainer;

import com.czy.log.Log;
import com.czy.log.LogFactory;
import com.czy.util.common.LifeCycle;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author chenzy
 * @date 2020-07-28
 */
public class MyTomcat implements LifeCycle {
    Log log = LogFactory.getLog(MyTomcat.class);
    private Status status;
    private ServerSocket server;
    private ServerInfo serverInfo;
    public static void main(String[] args) {

    }
    public MyTomcat() {
        //延时十秒,默认编码UTF-8
        this(new ServerInfo(8080,"localhost",10000,"UTF-8"));
    }

    public MyTomcat(ServerInfo serverInfo) {
        this.serverInfo=serverInfo;
        init();
    }

    @Override
    public Object init() {
        if (server==null){
            try {
                server = new ServerSocket(serverInfo.port(), 1, InetAddress.getByName(serverInfo.address()));
            } catch (IOException e) {
                log.error("启动服务失败，地址:["+serverInfo.address()+":"+serverInfo.port()+"]",e);
                return null;
            }
        }
        status=Status.Init;
        return null;
    }

    @Override
    public Object start() {
        status=Status.Start;
        return null;
    }

    @Override
    public Object exec() {
        status=Status.Exec;
        return null;
    }

    @Override
    public Object Loop() {
        status=Status.Loop;
        while (status.equals(Status.Loop)){
            if (server==null){
                status=Status.Destroy;
                return null;
            }
            try(var socket = server.accept()) {
                socket.setSoTimeout(serverInfo.timeout());
                //读取客户端请求
                var reader = socket.getInputStream();
                byte[] buf = new byte[1024];
                int len;
                var result = new StringBuilder();
                while ((len = reader.read(buf)) != -1) {
                    var temp = new String(buf, 0, len, serverInfo.charSet());
                    result.append(temp);
                }
            } catch (IOException e) {
                log.error("服务异常",e);
                e.printStackTrace();
                status=Status.Destroy;
                break;
            }

        }
        return null;
    }

    @Override
    public Object destroy() {
        status=Status.Destroy;
        return null;
    }
}
