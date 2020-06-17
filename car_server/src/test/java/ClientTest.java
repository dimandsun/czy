import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

/**
 * @author chenzy
 * @since 2020-06-17
 */
public class ClientTest {
    public static void main(String[] args) throws IOException {
        var socket = new Socket("127.0.0.1", 10089);
        //发送请求
        new Thread(() -> {
            try {
                var scanner = new Scanner(System.in);
                while (scanner.hasNext()) {
                    String questData = scanner.next();
                    var writer = socket.getOutputStream();
                    writer.write((questData).getBytes("UTF-8"));
                    writer.flush();
                    System.out.println("客户端发送请求数据：" + questData);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
        //接收响应
        var reader=socket.getInputStream();
        byte[] buf = new byte[1024];
        int len;
        while ((len = reader.read(buf)) != -1) {
            var data =new String(buf, 0, len, "UTF-8");
            System.out.println("客户端接收响应数据：" + data);
        }
    }
}
