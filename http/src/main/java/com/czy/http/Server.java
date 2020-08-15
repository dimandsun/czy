package com.czy.http;

import com.czy.http.exception.HttpException;
import com.czy.http.factory.RequestFactory;
import com.czy.http.factory.ResponseFactory;
import com.czy.http.model.ServerInfo;
import com.czy.http.pool.ConnectHandler;
import com.czy.http.pool.ConnectTask;
import com.czy.http.pool.ConnectThreadFactory;
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
 */
public class Server {
    public static final String CRLF = "\r\n";
    public static final String BANK = " ";
    private ForkJoinPool executor;
    private ReentrantLock lock = new ReentrantLock();
    /*为了防止一个请求生成多个线程任务。当前同时有n个请求连接，则connectTaskMap的个数为n*/
    private Map<SocketChannel,ConnectTask> connectTaskMap=new HashMap<>();
    public void close() {
        if (executor != null) {
            executor.shutdown();
            while (!executor.isTerminated()) {
                /*任务未完成，则等待*/
            }
        }
        ApplicationContext.instance().close();
    }

    public void start() {
        /*加载配置文件*/
//        ApplicationContext.getInstance().load();
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
            System.out.println("服务端开启了");
            System.out.println("=========================================================");
            //多路复用器开始监听
            while (ApplicationContext.instance().isActivity()) {
                selector.select();
                var iterator = selector.selectedKeys().iterator();
                while (iterator.hasNext()) {
                    //获取准备就绪的事件
                    var key = iterator.next();
                    if (key.isAcceptable()) {
                        //连接事件
                        var connect = ((ServerSocketChannel) key.channel()).accept().configureBlocking(false);
                        //连接注册读监听
                        connect.register(selector, SelectionKey.OP_READ);
                    } else if (key.isReadable()) {
                        var connect = (SocketChannel) key.channel();
                        if (!connectTaskMap.containsKey(connect)){
                            var task=new ConnectTask(connect, lock);
                            connectTaskMap.put(connect,task);
                            /*处理连接*/
                            Boolean result=executor().submit(task).get();
                            if (result){
                                connectTaskMap.remove(connect);
                            }else {
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

    public void stop() {
        System.out.println("=========================================================");
        System.out.println("程序结束");
        LogFactory.close();
        close();
    }

    public static void main(String[] args) throws UnknownHostException {


    }


}
