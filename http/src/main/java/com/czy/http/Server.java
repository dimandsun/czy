package com.czy.http;

import com.czy.http.exception.HttpException;
import com.czy.http.factory.RequestFactory;
import com.czy.http.factory.ResponseFactory;
import com.czy.http.model.ServerInfo;
import com.czy.util.io.FileUtil;
import com.czy.util.io.NIOUtil;
import com.czy.util.model.Par;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.charset.Charset;
import java.util.Date;

/**
 * @author chenzy
 * @date 2020-07-29
 */
public class Server {
    public static final String CRLF = "\r\n";
    public static final String BANK = " ";
    public void init(){
    }

    public static void main(String[] args) {
        var serverInfo = new ServerInfo(9090, "localhost", 100000, new Par<>("UTF-8"));
        var applicationContext = ApplicationContext.getInstance();
        applicationContext.setServerInfo(serverInfo);
        applicationContext.addServlet("/hello", "hello", HelloServlet.class);
        applicationContext.addServlet("/default", "default", DefaultServlet.class);
        applicationContext.initServlet();

        Runtime.getRuntime().addShutdownHook(new Thread(() -> System.out.println("程序结束")));
        try (var server = ServerSocketChannel.open()) {
            //非阻塞
            server.configureBlocking(false);
            //绑定ip
            server.bind(new InetSocketAddress(InetAddress.getByName(serverInfo.address()), serverInfo.port()));
            //设置延时
            server.socket().setSoTimeout(serverInfo.timeout());
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
