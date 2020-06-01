package com.czy.study.comfir;

import com.czy.MQUtil;
import com.rabbitmq.client.ConfirmListener;

import java.io.IOException;
import java.util.Collections;
import java.util.SortedSet;
import java.util.TreeSet;
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
 * 普通 发一条
 * 批量：
 * 异步confirm，提供回调函数
 */
public class Send2 {

    private static final String QUEUE_NAME = "hello.confirm";

    public static void main(String[] args) {
        try (var con = MQUtil.getCon();
             var channel = con.createChannel()) {

            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            //开启confirm模式，注意，事务模式的channel不能开始confirm
            channel.confirmSelect();
            //未确认的消息标识
            var confirmSet = Collections.synchronizedSortedSet(new TreeSet<Long>());

            /*
                每个消费者发送确认消息之前，消息队列不发送下一个消息到消费者，一次只处理一个消息
                限制发送给同一个消费者 不得超过一个消息
            */
            var perfetch = 1;
            channel.basicQos(perfetch);

            //
            channel.addConfirmListener(new ConfirmListener() {
                @Override
                public void handleAck(long l, boolean b) throws IOException {
                    if (b) {
                        System.out.println("handleAck-----mul");
                        confirmSet.headSet(l + 1).clear();
                    } else {
                        confirmSet.remove(l);
                        System.out.println("handleAck-----mul-----false");
                    }
                }

                @Override
                public void handleNack(long l, boolean b) throws IOException {
                    if (b) {
                        System.out.println("handleNack-----mul");
                        confirmSet.headSet(l + 1).clear();
                    } else {
                        confirmSet.remove(l);
                        System.out.println("handleNack-----mul-----false");
                    }
                }
            });
            while (true) {
                var seqNo = channel.getNextPublishSeqNo();
                String msg = "Hello World";
                channel.basicPublish("", QUEUE_NAME, null, msg.getBytes());
                confirmSet.add(seqNo);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
    }
}
