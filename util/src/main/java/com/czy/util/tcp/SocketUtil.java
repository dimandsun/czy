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
        InputStream reader = null;
        try {
            reader = socket.getInputStream();
            return new String(reader.readAllBytes(), "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
            IOUtil.close(reader);
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
    public static Boolean sendData(Socket socket, boolean isShortConnection, String... responseDataS) {
        if (responseDataS==null||responseDataS.length<1){
            return false;
        }
        OutputStream writer = null;
        try {
            for (var responseData:responseDataS){
                if (responseData==null){
                    continue;
                }
                writer = socket.getOutputStream();
                writer.write(responseData.getBytes("UTF-8"));
                writer.flush();
            }
            socket.shutdownOutput();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            IOUtil.close(writer);
            return false;
        } finally {
            if (isShortConnection && !socket.isOutputShutdown()) {
                try {
                    socket.shutdownOutput();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /*从套接字读取数据:默认短链接*/
    public static String readData(Socket socket) {
        return readData(socket, true);
    }

    /*通过套接字发送数据:默认短链接*/
    public static Boolean sendData(Socket socket, String... responseData) {
        return sendData(socket, true, responseData);
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
