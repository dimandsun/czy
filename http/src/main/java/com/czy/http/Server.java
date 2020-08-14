package com.czy.http;

import com.czy.http.exception.HttpException;
import com.czy.http.factory.RequestFactory;
import com.czy.http.factory.ResponseFactory;
import com.czy.http.model.ServerInfo;
import com.czy.javaLog.FileSetting;
import com.czy.log.LogFactory;
import com.czy.util.io.FileUtil;
import com.czy.util.io.NIOUtil;
import com.czy.util.model.Par;
import com.czy.util.model.SettingFile;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.charset.Charset;
import java.util.Date;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author chenzy
 * @date 2020-07-29
 */
public class Server {
    public static final String CRLF = "\r\n";
    public static final String BANK = " ";

    public void start() {
        var applicationContext = ApplicationContext.getInstance();

        /*加载配置文件*/
        applicationContext.load();
        /*或者不加载*/
        /*var serverInfo =ServerInfo.instance();
        serverInfo.setPort(9090);
        try {
            serverInfo.setAddress(InetAddress.getByName("localhost"));
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        serverInfo.setTimeout(100000);
        serverInfo.setCharset(Charset.forName("UTF-8"));*/
        /*初始化服务*/
        applicationContext.init();

        new ThreadPoolExecutor(1, 100, 60L, TimeUnit.SECONDS, new SynchronousQueue());
        /**/
    }

    public void stop() {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("程序结束");
            LogFactory.close();
            ApplicationContext.getInstance().close();
        }));
    }

    public static void main(String[] args) throws UnknownHostException {

        try (var server = ServerSocketChannel.open()) {
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
            while (applicationContext.isActivity()) {
                //多路复用器开始监听
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
                        /*连接*/
                        var connect = (SocketChannel) key.channel();
                        /*创建request、response对象*/
                        var request = RequestFactory.createRequest(connect);
                        var response = ResponseFactory.createResponse(request);
                        /*servlet执行业务方法*/
                        var servlet = applicationContext.getServlet(request.getRoute());
                        servlet.service(request, response);
                        //返回数据给客户端
                        response.beforeReturn();
                        connect.write(Charset.forName(response.getCharSet()).encode(response.getResult()));
                        NIOUtil.write(response.getFile(), connect);
                        //连接关闭
                        connect.close();
                        connect.close();
                    }
                    //取消选择建
                    iterator.remove();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (HttpException e) {
            e.printStackTrace();
        }

    }


}
