package com.czy.study.workfair;

import com.czy.MQUtil;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author chenzy
 * @since 2020-05-29
 *
 * 一个生产者发消息给一个队列，多个消费者从队列接收消息
 */
public class WorkSend {

    private static final String QUEUE_NAME="hello.workQueue";
    private static final String EXCHANGE_NAME="hello";
    public static void main(String[] args) {
        try(var con=MQUtil.getCon();
            var channel=con.createChannel()) {

            channel.queueDeclare(QUEUE_NAME,false,false,false,null);

            /*
                每个消费者发送确认消息之前，消息队列不发送下一个消息到消费者，一次只处理一个消息
                限制发送给同一个消费者 不得超过一个消息
            */
            var perfetch =1;
            channel.basicQos(perfetch);
            for (int i=0;i<100;i++){
                String msg ="Hello World"+i;
                channel.basicPublish("",QUEUE_NAME,null,msg.getBytes());
                Thread.sleep(1*20);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
