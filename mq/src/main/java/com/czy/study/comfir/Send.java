package com.czy.study.comfir;

import com.czy.MQUtil;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author chenzy
 * @since 2020-05-29
 * <p>
 * 消息确认：消息是否成功发送到mq服务器
 * amqp实现事务机制
 * Confirm模式
 * 开启Confirm模式 channel.confirmSelect();
 * 编程模式
 *  普通 发一条
 *  批量：
 *  异步confirm，提供回调函数
 */
public class Send {

    private static final String QUEUE_NAME = "hello.confirm";
    public static void main(String[] args) {
        try (var con = MQUtil.getCon();
             var channel = con.createChannel()) {

            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            //开启confirm模式，注意，事务模式的channel不能开始confirm
            channel.confirmSelect();
            /*
                每个消费者发送确认消息之前，消息队列不发送下一个消息到消费者，一次只处理一个消息
                限制发送给同一个消费者 不得超过一个消息
            */
            var perfetch = 1;
            channel.basicQos(perfetch);
            String msg = "Hello World";
            channel.basicPublish("", QUEUE_NAME, null, msg.getBytes());
            channel.basicPublish("", QUEUE_NAME, null, msg.getBytes());
            if (channel.waitForConfirms()){
                System.out.println("发送成功");
            }else {
                System.out.println("发送失败");
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
