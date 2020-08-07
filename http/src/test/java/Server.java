import com.czy.http.model.ServerInfo;
import com.czy.javaLog.Log;
import com.czy.javaLog.LogFactory;
import com.czy.util.model.Par;
import com.czy.util.text.Line;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.util.Date;

/**
 * @author chenzy
 * @date 2020-07-28
 */
public class Server {
    Log log = LogFactory.getLog(Server.class);
    private static final String CRLF = "\r\n";
    private static final String BANK = " ";
    private ServerInfo serverInfo;
    private ServerSocket server;

    public Server() {
        //延时十秒,默认编码UTF-8
        this(new ServerInfo(9090, "localhost", 10000, new Par<>("UTF-8")));
    }

    public Server(ServerInfo serverInfo) {
        this.serverInfo = serverInfo;
    }

    public void start() {
        if (server == null) {
            try {
                server = new ServerSocket(serverInfo.port(), 1, InetAddress.getByName(serverInfo.address()));
                this.receive();
            } catch (IOException e) {
                log.error("启动服务失败，地址:[" + serverInfo.address() + ":" + serverInfo.port() + "]", e);
            }
        }
    }

    private void receive() {
        while (true) {
            try {
                var connect = server.accept();
                var msg = new StringBuilder();
                String line = null;
                var reader = new BufferedReader(new InputStreamReader(connect.getInputStream()));
                while ((line = reader.readLine()).length() > 0) {
                    msg.append(line).append(Line.separator);
                }
                System.out.println(msg);
            /*var bytes=new byte[1024*16];
            var length=connect.getInputStream().read(bytes);
            msg.append(new String(bytes,0,length));
            System.out.println(msg.toString());*/

                var responseBody = """
                        <!DOCTYPE html>
                        <html>
                            <head>
                                <meta charset="UTF-8"/>
                                <title></title>
                            </head>
                            <body bgcolor="white">
                                <h1>手写嵌入式Tomcat!</h1>
                            </body>
                        </html>    
                       """;
                var response = new StringBuilder();
                response.append("HTTP/1.1").append(BANK).append(200).append(BANK).append("OK").append(CRLF)
                        .append("Server:czy Server/0.0.1").append(CRLF)
                        .append("Date:" + new Date()).append(CRLF)
                        .append("Content-type:text/html;charset=").append(serverInfo.charSet().get()).append(CRLF)
                        .append("Content-Length:").append(responseBody.getBytes().length).append(CRLF)
                        .append("").append(CRLF)
                        .append(responseBody).append(CRLF)
                ;
                var writer = new BufferedWriter(new OutputStreamWriter(connect.getOutputStream()));
                writer.write(response.toString());
                writer.flush();
                connect.close();
                System.out.println(response);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        Server server = new Server();
        server.start();
    }
}
