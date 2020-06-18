package com.czy.javase.study.tcp;

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

 */
public class TCP {
}
