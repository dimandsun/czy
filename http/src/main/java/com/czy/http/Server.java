package com.czy.http;

import com.czy.http.exception.HttpException;
import com.czy.http.factory.RequestFactory;
import com.czy.http.factory.ResponseFactory;
import com.czy.http.model.ServerInfo;
import com.czy.http.pool.ConnectHandler;
import com.czy.http.pool.ConnectTask;
import com.czy.http.pool.ConnectThreadFactory;
import com.czy.log.Log;
import com.czy.log.LogFactory;
import com.czy.util.ThreadUtil;
import com.czy.util.io.NIOUtil;
import com.czy.util.text.StringUtil;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.nio.channels.*;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.*;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Consumer;

/**
 * @author chenzy
 * @date 2020-07-29
 * 当前版本不支持servlet注解
 */
public class Server {
    private static Log log = LogFactory.getLog("server");
    public static final String CRLF = "\r\n";
    public static final String BANK = " ";
    private ForkJoinPool executor;
    private ReentrantLock lock = new ReentrantLock();
    private Consumer stopTask;
    /*为了防止一个请求生成多个线程任务。当前同时有n个请求连接，则connectTaskMap的个数为n*/
    private Map<SocketChannel, ConnectTask> connectTaskMap = new HashMap<>();

    public void close() {
        if (executor != null) {
            executor.shutdown();
            while (!executor.isTerminated()) {
                /*任务未完成，则等待*/
            }
        }
        ApplicationContext.instance().close();
    }
    public void stop() {
        log.info("=========================================================");
        log.info("开始销毁程序");
        if (stopTask==null){
            close();
            LogFactory.close();
        }else {
            stopTask.accept(null);
        }
        System.err.println("程序销毁完成");
    }
    public void stop(Consumer consumer) {
        stopTask=consumer;
    }
    public void start() {
        /*初始化服务容器*/
        ApplicationContext.instance().init();
        /*程序结束时调用*/
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            stop();
        }));
        /*启动服务*/
        try (var server = ServerSocketChannel.open()) {
            var serverInfo = ServerInfo.instance();
            //非阻塞
            server.configureBlocking(false);
            //绑定ip
            server.bind(new InetSocketAddress(serverInfo.getAddress(), serverInfo.getPort()));
            //设置延时
            server.socket().setSoTimeout(serverInfo.getTimeout());
            //通道注册到选择器,监听连接事件
            var selector = Selector.open();
            server.register(selector, SelectionKey.OP_ACCEPT);
            log.info("服务端程序开启");
            log.info("=========================================================");
            //多路复用器开始监听
            while (ApplicationContext.instance().isActivity()) {
                selector.select();
                var iterator = selector.selectedKeys().iterator();
                while (iterator.hasNext()) {
                    //获取准备就绪的事件
                    var key = iterator.next();
                    if (key.isAcceptable()) {
                        //连接注册读事件
                        ((ServerSocketChannel) key.channel()).accept().configureBlocking(false).register(selector, SelectionKey.OP_READ);
                    } else if (key.isReadable()) {
                        var connect = (SocketChannel) key.channel();
                        if (!connectTaskMap.containsKey(connect)) {
                            var task = new ConnectTask(connect);
                            connectTaskMap.put(connect, task);
                            /*处理连接*/
                            if (executor().submit(task).isDone()){
                                /*任务没有完成时不会remove*/
                                connectTaskMap.remove(connect);
                            }
                        }
                    }
                    iterator.remove();
                }
            }
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }

    private ExecutorService executor() {
        lock.lock();
        /*锁确保不会生成多个线程池*/
        if (executor == null) {
            var threadNum = ThreadUtil.getThreadNum();
            executor = new ForkJoinPool(threadNum, new ConnectThreadFactory(), new ConnectHandler(), false
                    , threadNum, threadNum * 2, 1, null, 10, TimeUnit.SECONDS);
        }
        lock.unlock();
        return executor;
    }


    public static void main(String[] args) throws UnknownHostException {


    }


}
