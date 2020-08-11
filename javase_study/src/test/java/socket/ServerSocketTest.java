package socket;

import com.czy.util.time.DateUtil;
import com.czy.util.io.IOUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.util.Scanner;

import static com.czy.util.tcp.SocketUtil.readData;
import static com.czy.util.tcp.SocketUtil.sendData;

/**
 * @author chenzy
 * @since 2020-06-17
 */
public class ServerSocketTest {
    public static void main(String[] args) throws IOException {
        int port = 10089;
        /*backlog:设值最大等待队列长度。等待队列满了再想连接，会报ConnectException：Connection refused：connect
            默认50
          bindAddr:绑定多个ip。（一台电脑多个网卡，一个网卡一个ip。或者一个网卡多个ip）
        */
        var server = new ServerSocket(port,3, InetAddress.getLocalHost());
        //获取连接
        var connect = server.accept();
        //读取数据
        var reader = new BufferedReader(new InputStreamReader(connect.getInputStream()));
        String data="";
        while (!"".equals(data=reader.readLine())){
            System.out.println(data);
        }
        //响应：输出数据
        var out=connect.getOutputStream();
        var result= """
                HTTP/1.1 200 OK
                
                <html>
                <body>兄弟，看到没！</body>
                </html>
                """;
        out.write(result.getBytes());
        out.flush();
        //关闭资源
        connect.close();
        server.close();
    }

    public static void testServer() throws IOException {
        int port = 10089;
        var serverSocket = new ServerSocket(port);
        System.out.println("服务器启动成功ip:" + serverSocket.getInetAddress().getHostAddress() + ":" + port);
        var socket = serverSocket.accept();
        System.out.println(DateUtil.now() + "  客户端 （" + socket.getInetAddress().getHostAddress() + "） 连接成功...");
        //向客户端发送响应
        new Thread(() -> {
            try {
                var scanner = new Scanner(System.in);
                while (scanner.hasNext()) {
                    String responseData = scanner.next();
                    var writer = socket.getOutputStream();
                    writer.write((responseData).getBytes("UTF-8"));
                    writer.flush();
                    System.out.println("服务器发送响应结果：" + responseData);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();

        //读取客户端请求
        var reader = socket.getInputStream();
        byte[] buf = new byte[1024];
        int len;
        while ((len = reader.read(buf)) != -1) {
            var data = new String(buf, 0, len, "UTF-8");
            System.out.println("服务器接收请求数据：" + data);
        }
    }
}
