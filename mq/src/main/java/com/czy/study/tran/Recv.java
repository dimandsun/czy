package com.czy.study.tran;

import com.czy.MQUtil;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

import java.io.IOException;

/**
 * @author chenzy
 * @since 2020-05-29
 */
public class Recv {

    private static final String QUEUE_NAME="hello.tx";

    public static void main(String[] args) {
        try {
            var con = MQUtil.getCon();
            var channel = con.createChannel();

            channel.queueDeclare(QUEUE_NAME, false, false, false, null);

            //一次分发一个消息
            channel.basicQos(1);

            var  autoAck=false;//自动应答
            //autoAck=true时，消息发送给消费者后，消息就会从内存中删除。
            // 此时杀死消费者，会丢失正在处理的消息
            //autoAck=false 如果消费者挂了，会把正在处理的消息交给其他消费者
            //消费者处理完消息后，mq删除消息
            channel.basicConsume(QUEUE_NAME, autoAck, new DefaultConsumer(channel) {
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                    String message = new String(body, "UTF-8");
                    System.out.println(" Received '" + message + "'");
                    try {
                        Thread.sleep(2000);
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
