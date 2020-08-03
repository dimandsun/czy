package com.czy.http;

import com.czy.http.exception.HttpException;
import com.czy.http.factory.RequestFactory;
import com.czy.http.factory.ResponseFactory;
import com.czy.http.model.ServerInfo;
import com.czy.util.io.NIOUtil;
import com.czy.util.model.Par;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.channels.*;
import java.util.Date;

/**
 * @author chenzy
 * @date 2020-07-29
 */
public class Server {
    public static final String CRLF = "\r\n";
    public static final String BANK = " ";
    public static void main(String[] args) {
        var serverInfo=new ServerInfo(9090,"localhost",100000,new Par<>("UTF-8"));
        var applicationContext= ApplicationContext.getInstance();
        applicationContext.setServerInfo(serverInfo);
        applicationContext.addServlet("/hello","hello", HelloServlet.class);
        applicationContext.addServlet("/default","default", DefaultServlet.class);
        applicationContext.initServlet();

        Runtime.getRuntime().addShutdownHook(new Thread(() -> System.out.println("程序结束")));

        try(var server = ServerSocketChannel.open()) {
            //置为非阻塞
            server.configureBlocking(false);
            //绑定连接
            server.bind(new InetSocketAddress(InetAddress.getByName(serverInfo.address()),serverInfo.port()));
            //设置延时
            server.socket().setSoTimeout(serverInfo.timeout());

            //获取选择器
            var selector = Selector.open();
            //服务注册到选择器,指定监听事件
            server.register(selector, SelectionKey.OP_ACCEPT);
            System.out.println("服务端开启了");
            System.out.println("=========================================================");
            //轮询获取选择器上已经准备就绪的事件
            while (selector.select() > 0){
                var iterator = selector.selectedKeys().iterator();
                while (iterator.hasNext()) {
                    //获取准备就绪的事件
                    var selectionKey = iterator.next();
                    if (selectionKey.isAcceptable()) {
                        //接收就绪
                        var socketChannel = server.accept();
                        //切换非阻塞模式
                        socketChannel.configureBlocking(false);
                        //将该通道注册到选择器上
                        socketChannel.register(selector, SelectionKey.OP_READ);
                    }
                }
            }
            while (true) {
                //获取客户端连接的通道
                SocketChannel connectChannel = server.accept();
                if(connectChannel == null){
                    continue;
                }
                //创建request、response对象
                var request = RequestFactory.createRequest(applicationContext,connectChannel);
                var response= ResponseFactory.createResponse(request);
                //servlet执行业务方法
                var servlet=applicationContext.getServlet(request.getRoute());
                servlet.service(request,response);

                //返回数据给客户端
                response.beforeReturn();
                var result=response.getResult();
                NIOUtil.write(result,response.getFile(),connectChannel);
                //请求结束
                connectChannel.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (HttpException e) {
            e.printStackTrace();
        }

    }



}
