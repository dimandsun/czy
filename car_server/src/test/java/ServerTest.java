import com.czy.util.DateUtil;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.Scanner;

/**
 * @author chenzy
 * @since 2020-06-17
 */
public class ServerTest  {
    public static void main(String[] args) throws IOException {
        int port=10089;
        var serverSocket = new ServerSocket(port);
        System.out.println("服务器启动成功ip:"+serverSocket.getInetAddress().getHostAddress()+":"+port);
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
        var reader=socket.getInputStream();
        byte[] buf = new byte[1024];
        int len;
        while ((len = reader.read(buf)) != -1) {
            var data =new String(buf, 0, len, "UTF-8");
            System.out.println("服务器接收请求数据：" + data);
        }
    }
}
