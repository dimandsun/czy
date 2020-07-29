package com.czy.httpcontainer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.ServerSocketChannel;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 * @author chenzy
 * @date 2020-07-29
 */
public class Test {
    public static void main(String[] args) {
        try {
            //获取通道
            var channel = ServerSocketChannel.open();
            //绑定连接
            channel.bind(new InetSocketAddress(10089));

            //获取客户端连接的通道
            var socketChannel = channel.accept();

            //分配指定大小的缓冲区
            var buffer = ByteBuffer.allocate(1024);
            //接收客户端数据，并保存
            while (socketChannel.read(buffer) != -1) {
                buffer.flip();
//                buffer;

                buffer.clear();
            }
            //关闭通道
            channel.close();
            socketChannel.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
