package socket.tcp;

/**
 * @author chenzy
 * @since 2020-06-18
"GET / HTTP/1.0\n"
curl www.baidu.con
exec 8<> /dev/tcp/www.baidu.com/80 8与百度建立连接
echo -e "GET / HTTP/1.0\n" 1>& 8 发送http协议的请求头给8
cat <& 8  从8获取数据

 udp/tcp：面向连接可靠的传输协议
 三次握手：
    syn
    syn+ack
    ack
 三次握手后开辟资源连接
 传输数据
 socket套接字：ip:port+ip+ip:port
 四次分手：
    fin->
    <-fin+ack
    <-fin
    ack->

 抓包：tcpdump -nn -i eth0 port 80

 udp协议 无连接：耗资小，传输快，可能会数据丢失
 tcp协议：建立可靠连接。三次握手后才能通信

 TCP：基于流、长连接。以流为单位进行数据传输，发送的数据带有顺序性
 长连接：连接建立后，保持开启状态(无论是否使用这个连接)，数据传输完后不关闭。
 短连接：数据传输完后连接关闭。
 链接：服务端与客户端确认彼此存在的过程。
 建立连接：三次握手

 */
public class TCP {
}
