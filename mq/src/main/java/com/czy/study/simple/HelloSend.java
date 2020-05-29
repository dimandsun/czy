package com.czy.study.simple;

import com.czy.MQInfo;
import com.czy.MQUtil;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author chenzy
 * @since 2020-05-29
 */
public class HelloSend {
    private static final String QUEUE_NAME="hello.queue";
    private static final String EXCHANGE_NAME="hello";
    public static void main(String[] args) {
        try(var con=MQUtil.getCon();var channel=con.createChannel();){
            channel.queueDeclare(QUEUE_NAME,false,false,false,null);
            String message = "Hello World!";
            channel.basicPublish("",QUEUE_NAME,null,message.getBytes());
        } catch (TimeoutException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


       /* var factory=new ConnectionFactory();
        factory.setHost(MQInfo.getInstance().getHost());
        try (var connection = factory.newConnection();
             var channel = connection.createChannel()) {

            //创建队列
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);

//            channel.queueDeclare();//创建一个非持久的，排他的，自动删除的随机名称的队列：

            //fanout类型的交换机
            channel.exchangeDeclare(EXCHANGE_NAME,"fanout");
            //交换机与队列绑定
            channel.queueBind(QUEUE_NAME,EXCHANGE_NAME,"");

            String message = "Hello World!";

            //参数1是交换机name
            channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
            System.out.println(" [x] Sent '" + message + "'");

        } catch (TimeoutException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }*/

    }
}
