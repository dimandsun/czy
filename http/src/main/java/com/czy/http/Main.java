package com.czy.http;

/**
 * @author chenzy
 * @date 2020-08-15
 */
public class Main {
    public static void main(String[] args) {
        ApplicationContext.instance().load();
        var server=new Server();
        server.start();
    }
}
