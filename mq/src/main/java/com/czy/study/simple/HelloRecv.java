package com.czy.study.simple;

import com.czy.MQInfo;
import com.czy.MQUtil;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author chenzy
 * @since 2020-05-29
 */
public class HelloRecv {
    private static final String QUEUE_NAME="hello.queue";
    private static final String EXCHANGE_NAME="hello";

    public static void main(String[] args) {
        try {
            var con=MQUtil.getCon();
            var channel=con.createChannel();
            channel.queueDeclare(QUEUE_NAME,false,false,false,null);
            channel.basicConsume(QUEUE_NAME, true,new DefaultConsumer(channel){
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                    String message = new String(body, "UTF-8");
                    System.out.println(" [x] Received '" + message + "'");
                }
            } );
        } catch (IOException e) {
            e.printStackTrace();
        }



        /*try  {
            var connection = MQUtil.getCon();
            var channel = connection.createChannel();
            channel.queueDeclare(QUEUE_NAME,false,false,false,null);
            channel.queueBind(QUEUE_NAME,EXCHANGE_NAME,"");
            channel.basicQos(1); //一次仅接受一条未经确认的消息
            System.out.println("[*]等待消息。要退出,请按CTRL + C" );
            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                String message = new String(delivery.getBody(), "UTF-8");
                System.out.println(" [x] Received '" + message + "'");
                channel.basicAck(delivery.getEnvelope().getDeliveryTag(),false);
            };
            channel.basicConsume(QUEUE_NAME, true, deliverCallback, consumerTag -> { });
        }catch (IOException e) {
            e.printStackTrace();
        }*/

    }
}
