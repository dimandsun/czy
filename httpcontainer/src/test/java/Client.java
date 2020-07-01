
import com.czy.util.io.IOUtil;
import com.czy.util.tcp.HttpRequestUtil;
import com.czy.util.tcp.SocketUtil;
/**
 * @author chenzy
 * @date 2020-06-29
 */
public class Client {

    public static void main(String[] args) throws InterruptedException {
        var host = "127.0.0.1";
        var port = 10089;

        int i=1;
        while (true){
            //获取连接
            var socket = SocketUtil.getConnection(host, port);
            if (socket==null){
                System.out.println("无法连接指定服务器，请检查");
                continue;
            }
            HttpRequestUtil
            //发送数据
            SocketUtil.sendData(socket, "数据" + i);
            //读取数据
            String result = SocketUtil.readData(socket);
            //关闭连接
            IOUtil.close(socket);
            System.out.println(result);
            Thread.sleep(1000);
            i++;
        }
    }
}
