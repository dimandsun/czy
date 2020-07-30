package com.czy.http;

import com.czy.http.factory.RequestFactory;
import com.czy.http.factory.ResponseFactory;
import com.czy.http.model.Request;
import com.czy.http.model.Response;
import com.czy.http.model.ServerInfo;
import com.czy.util.model.Par;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.ServerSocketChannel;
import java.nio.charset.Charset;

/**
 * @author chenzy
 * @date 2020-07-29
 */
public class Test {
    public static void main(String[] args) {
        var serverInfo=new ServerInfo(9090,"localhost",100000,new Par<>("UTF-8"));
        var applicationContext= ApplicationContext.getInstance();
        applicationContext.addServlet("/","hello", HelloServlet.class);
        applicationContext.initServlet();
        try(var server = ServerSocketChannel.open()) {
            //绑定连接
            server.bind(new InetSocketAddress(InetAddress.getByName(serverInfo.address()),serverInfo.port()));
            while (true) {

                //获取客户端连接的通道
                var socketChannel = server.accept();
                socketChannel.socket().setSoTimeout(serverInfo.timeout());
                InputStream inStream = socketChannel.socket().getInputStream();
                ReadableByteChannel wrappedChannel = Channels.newChannel(inStream);

                Request request = RequestFactory.createRequest(applicationContext,wrappedChannel);
                Response response= ResponseFactory.createResponse(request);
                var servlet=applicationContext.getServlet(request.getRoute());
                Object result=servlet.service(request,response);

                var buffer = ByteBuffer.allocate(1024*4);
                ByteBuffer lineBuffer = ByteBuffer.allocate(20);
                //接收客户端数据，并保存
                int lineCount = 0;
                while (wrappedChannel.read(buffer) != -1) {
                    buffer.flip();
                    while (buffer.hasRemaining()){
                        byte b = buffer.get();
                        lineBuffer.put(b);
                        //空间不够扩容
                        if (!lineBuffer.hasRemaining()){
                            lineBuffer = reAllocate(lineBuffer);
                        }
                        //一行一行读取
                        if (b == 10 || b == 13) { // 换行或回车
                            lineBuffer.flip();
                            final var line = Charset.forName(serverInfo.charSet().get()).decode(lineBuffer).toString();

                            System.out.print(line);
                            lineCount++;
                            lineBuffer.clear();
                        }
                    }
                    buffer.clear();
                }
                //关闭通道
                wrappedChannel.close();
                socketChannel.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * 扩容
     * @param stringBuffer
     * @return
     */
    private static ByteBuffer reAllocate(ByteBuffer stringBuffer) {
        final int capacity = stringBuffer.capacity();
        byte[] newBuffer = new byte[capacity * 2];
        System.arraycopy(stringBuffer.array(), 0, newBuffer, 0, capacity);
        return ByteBuffer.wrap(newBuffer).position(capacity);
    }
}
