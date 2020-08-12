package com.czy.util.tcp;

import com.czy.util.io.IOUtil;

import java.io.*;
import java.net.Socket;

/**
 * @author chenzy
 * @since 2020-06-17
 */
public class SocketUtil {
    private SocketUtil() {
    }



    /*从套接字读取数据*/
    public static String readData(Socket socket, boolean isShortConnection) {
        try(var reader = socket.getInputStream()) {
            return new String(reader.readAllBytes(), "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            if (isShortConnection && !socket.isInputShutdown()) {
                try {
                    socket.shutdownInput();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /*通过套接字发送数据*/
    public static Boolean sendData(Socket socket, String... responseDataS) {
        if (responseDataS==null||responseDataS.length<1){
            return false;
        }
        try(var writer = socket.getOutputStream()) {
            for (var responseData:responseDataS){
                if (responseData==null){
                    continue;
                }
                writer.write(responseData.getBytes("UTF-8"));
            }
            writer.flush();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    public static String readData(Socket socket) {
        return readData(socket, true);
    }
    public static Socket getConnection(String host, Integer port) {
        try {
            return new Socket(host, port);
        } catch (IOException e) {
//            无法连接指定服务器，请检查
            return null;
        }
    }
}
