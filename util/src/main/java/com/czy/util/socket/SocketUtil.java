package com.czy.util.socket;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.Socket;

/**
 * @author chenzy
 * @since 2020-06-17
 */
public class SocketUtil {
    private SocketUtil() {
    }
    /*从套接字读取数据*/
    public static String readData(Socket socket) {
        try {
            var reader=socket.getInputStream();
            byte[] buf = new byte[1024];
            int len;
            while ((len = reader.read(buf)) != -1) {
                var data =new String(buf, 0, len, "UTF-8");
                return data;
            }
            return null;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    /*向套接字发送数据*/
    public static Boolean sendData(Socket socket, String responseData) {
        try  {
            var writer = socket.getOutputStream();
            writer.write((responseData).getBytes("UTF-8"));
            writer.flush();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

    }
}
