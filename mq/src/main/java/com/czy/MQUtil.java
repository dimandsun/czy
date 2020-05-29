package com.czy;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author chenzy
 * @since 2020-05-29
 */
public class MQUtil {

    public static Connection getCon(){
        var factory=new ConnectionFactory();
        var mqInfo=MQInfo.getInstance();
        factory.setHost(mqInfo.getHost());
        factory.setPort(mqInfo.getPort());
        factory.setUsername(mqInfo.getUserName());
        factory.setPassword(mqInfo.getPS());
        try {
            return factory.newConnection();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } catch (TimeoutException e) {
            e.printStackTrace();
            return null;
        }
    }
}
