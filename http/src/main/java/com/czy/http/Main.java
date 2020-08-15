package com.czy.http;

/**
 * @author chenzy
 * @date 2020-08-15
 */
public class Main {
    public static void main(String[] args) {
        /*加载配置属性*/
        ApplicationContext.instance().load();
        /*启动服务*/
        new Server().start();
    }
}
