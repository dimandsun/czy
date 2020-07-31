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
import java.net.http.HttpResponse;
import java.nio.channels.*;

/**
 * @author chenzy
 * @date 2020-07-29
 */
public class Test {
    public static void main(String[] args) {
        var serverInfo=new ServerInfo(9090,"localhost",100000,new Par<>("UTF-8"));
        var applicationContext= ApplicationContext.getInstance(serverInfo);
        applicationContext.addServlet("/","hello", HelloServlet.class);
        applicationContext.initServlet();
        try(var server = ServerSocketChannel.open()) {
            //置为非阻塞
            server.configureBlocking(false);
            //绑定连接
            server.bind(new InetSocketAddress(InetAddress.getByName(serverInfo.address()),serverInfo.port()));
            //设置延时
            server.socket().setSoTimeout(serverInfo.timeout());
//            Selector selector = Selector.open();
            //注册OP_ACCEPT事件：如果有客户端发来连接请求，则该键在select()后被选中）
//            server.register(selector, SelectionKey.OP_ACCEPT);
            System.out.println("服务端开启了");
            System.out.println("=========================================================");
            while (true) {
                //选择准备好的事件
//                selector.select();
                //获取客户端连接的通道
                SocketChannel connectChannel = server.accept();
                if(connectChannel == null){
                    continue;
                }
                //创建request、response对象
                var request = RequestFactory.createRequest(applicationContext,connectChannel);
                if (1==1)continue;
                var response= ResponseFactory.createResponse(request);
                //servlet执行业务方法
                var servlet=applicationContext.getServlet(request.getRoute());
                servlet.service(request,response);
                //返回数据给客户端
                var result= response.getBody();
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
