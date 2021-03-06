package socket.http;

import com.czy.util.io.IOUtil;

import java.io.IOException;
import java.net.ServerSocket;

import static com.czy.util.tcp.SocketUtil.readData;
import static com.czy.util.tcp.SocketUtil.sendData;

/**
 * @author chenzy
 * @since 2020-06-17
 */
public class ServerHTTP {
    public static void main(String[] args){
        int port = 9090;
        try(var serverSocket = new ServerSocket(port)) {
            while (true) {
                //监听连接
                try(var socket = serverSocket.accept()) {
                    new Thread(()->{
                        System.out.println(1);
                        while (true){
                            System.out.println(2);
                            System.out.println(3);
                            //读取数据
                            var data = readData(socket);
                            System.out.println(4);
                            System.out.println(data);
                            //发送响应
                            sendData(socket, data + "123");
                        }
                    }).start();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
