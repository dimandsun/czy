package com.czy.util.io;

/**
 * @author chenzy
 * @since 2020-06-18
 * 传统IO： 面向流
 *         阻塞IO
 * NIO：   面向缓冲区：通道Channel不带数据，缓冲区Buffer装载数据。
 *              通道：打开IO设备(file/socket)的连接。负责传输
 *              缓冲区：负责存储
 *         非阻塞
 *         选择器
 *    获取channel、buffer——》操作buffer——》处理数据
 */
public class NIOUtil {
    private NIOUtil(){

    }


}
