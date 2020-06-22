package com.czy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * @author chenzy
 * @since 2020-06-17
 */
public class ServerThread extends Thread {
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;

    public ServerThread(Socket socket) throws IOException {
        this.socket = socket;
        in = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
        out = new PrintWriter(socket.getOutputStream(), true);
        start();
    }

    @Override
    public void run() {
        try {
            while (true) {
                String line = in.readLine();
                if (line == null || "".equals(line.strip())) { //是否终止会话
                    break;
                }
                System.out.println("Received  message: " + line);
                // 通过输出流向客户端发送信息
                out.println(line);
                out.flush();

            }

            out.close();
            in.close();
            socket.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
