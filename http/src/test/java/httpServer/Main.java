package httpServer;

import com.czy.http.model.ServerInfo;
import com.czy.util.model.Par;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;

/**
 * @author chenzy
 * @date 2020-08-01
 */
public class Main {
    public static void main(String[] args) {
        var serverInfo=new ServerInfo(9090,"localhost",100000,new Par<>("UTF-8"));
        try {
            var server= HttpServer.create(new InetSocketAddress(serverInfo.port()),0);
            server.createContext("/start", new StartHandler());
            server.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
