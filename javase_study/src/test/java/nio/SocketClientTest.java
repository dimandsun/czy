package nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Date;
import java.util.Scanner;

/**
 * @author chenzy
 * @description
 * @since 2020/6/18
 */
public class SocketClientTest {
    public static void main(String[] args) throws IOException {
        //获取通道
        var channel = SocketChannel.open(new InetSocketAddress("127.0.0.1", 10089));
        //切换成非阻塞模式
        channel.configureBlocking(false);
        //发送数据到服务器
        var buffer = ByteBuffer.allocate(1024);
        var scanner=new Scanner(System.in);
        while ((scanner.hasNext())){
            var str=scanner.next();
            buffer.put((new Date().toString()+":"+str).getBytes());
            buffer.flip();
            channel.write(buffer);
            buffer.clear();
        }



        //接收服务端反馈
        /*int len = 0;
        while ((len=channel.read(buffer))!=-1){
            buffer.flip();
            System.out.println(new String(buffer.array(),0,len));
            buffer.clear();
        }*/
        //关闭通道
        channel.close();
    }
}
