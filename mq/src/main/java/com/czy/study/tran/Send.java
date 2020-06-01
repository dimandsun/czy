package com.czy.study.tran;

import com.czy.MQUtil;
import com.rabbitmq.client.Channel;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author chenzy
 * @since 2020-05-29
 *
 * 消息确认：消息是否成功发送到mq服务器
 * amqp实现事务机制
 *      txSelect 把当前channel设值成事务模式
 *      txCommit 提交事务
 *      txRollback 回滚事务
 * Confirm模式
 */
public class Send {

    private static final String QUEUE_NAME="hello.tx";
    public static void main(String[] args) {
        Channel channel= null;
        try(var con=MQUtil.getCon();
            ) {
            channel=con.createChannel();

            channel.queueDeclare(QUEUE_NAME,false,false,false,null);

            /*
                每个消费者发送确认消息之前，消息队列不发送下一个消息到消费者，一次只处理一个消息
                限制发送给同一个消费者 不得超过一个消息
            */
            var perfetch =1;
            channel.basicQos(perfetch);
            for (int i=0;i<20;i++){
                String msg ="Hello World"+i;
                channel.txSelect();
                channel.basicPublish("",QUEUE_NAME,null,msg.getBytes());
                Thread.sleep(1*20);

                channel.txCommit();
            }
        } catch (IOException e) {
            e.printStackTrace();
            try {
                channel.txRollback();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
            try {
                channel.txRollback();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }finally {
            if (channel!=null){
                try {
                    channel.close();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (TimeoutException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}
