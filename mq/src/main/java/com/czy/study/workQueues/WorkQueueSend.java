package com.czy.study.workQueues;

import com.czy.MQUtil;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author chenzy
 * @since 2020-05-29
 *
 * 一个生产者发消息给一个队列，多个消费者从队列接收消息
 */
public class WorkQueueSend {

    private static final String QUEUE_NAME="hello.workQueue";
    private static final String EXCHANGE_NAME="hello";
    public static void main(String[] args) {
        try(var con=MQUtil.getCon();
            var channel=con.createChannel()) {

            channel.queueDeclare(QUEUE_NAME,false,false,false,null);

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
