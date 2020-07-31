package com.czy.http.factory;

import com.czy.http.ApplicationContext;
import com.czy.http.exception.HttpException;
import com.czy.http.model.Request;
import com.czy.util.io.NIOUtil;
import com.czy.util.list.Iterables;

import java.io.InputStream;
import java.nio.channels.ByteChannel;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.SocketChannel;
import java.util.List;

/**
 * @author chenzy
 * @date 2020-07-30
 */
public class RequestFactory {
    private RequestFactory() {
    }

    public static Request createRequest(ApplicationContext applicationContext, SocketChannel socketChannel) throws HttpException {
        var data= NIOUtil.readByLine(applicationContext.getServerInfo().charSet().get(), socketChannel);
        if (data==null||data.isEmpty()){
            System.out.println("---data为空----------------------");
//            throw new HttpException("创建request失败");
        }
        Iterables.forEach(data,(index,line)->{
            if (index==0){
                /*首行：*/
            }
            System.out.println(line);
        });
        return null;
    }
}
