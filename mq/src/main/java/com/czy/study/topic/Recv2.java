package com.czy.study.topic;

import com.czy.MQUtil;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

import java.io.IOException;

/**
 * @author chenzy
 * @since 2020-05-29
 * <p>
 * 一个生产者发消息给一个队列，多个消费者从队列接收消息
 */
public class Recv2 {

    private static final String QUEUE_NAME = "hello.topic2";
    private static final String EXCHANGE_NAME = "exchange_topic";

    public static void main(String[] args) {
        try {
            var con = MQUtil.getCon();
            var channel = con.createChannel();
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            //绑定队列到交换机
            channel.queueBind(QUEUE_NAME,EXCHANGE_NAME,"log.#");
            //一次分发一个消息
            channel.basicQos(1);
            var  autoAck=false;//自动应答
            channel.basicConsume(QUEUE_NAME, autoAck, new DefaultConsumer(channel) {
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                    String message = new String(body, "UTF-8");
                    System.out.println(" Received '" + message + "'");
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }finally {
                        //手动回执
                        channel.basicAck(envelope.getDeliveryTag(),autoAck);
                    }
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
