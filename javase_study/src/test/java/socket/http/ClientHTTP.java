package socket.http;

import com.czy.util.tcp.HttpRequestUtil;

/**
 * @author chenzy
 * @since 2020-06-18
 */
public class ClientHTTP {
    public static void main(String[] args) throws InterruptedException {
        int i=1;
        while (true){

            HttpRequestUtil.sendGet("http://127.0.0.1:8080/abc",null);
            /*//获取连接
            var socket = getConnection("127.0.0.1", 8080);
            if (socket==null){
                System.out.println("无法连接指定服务器，请检查");
                continue;
            }
            //发送数据
            sendData(socket, "数据" + i);
            //读取数据
            String result = readData(socket);
            //关闭连接
            close(socket);
            System.out.println(result);
            Thread.sleep(1000);*/
            i++;
        }

    }
}
