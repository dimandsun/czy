package com.czy.http.factory;

import com.czy.http.ApplicationContext;
import com.czy.http.Server;
import com.czy.http.exception.HttpException;
import com.czy.http.model.QuestScheme;
import com.czy.http.model.Request;
import com.czy.http.model.ServletInfo;
import com.czy.util.enums.QuestMethodEnum;
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
            throw new HttpException("创建request失败,未读取到数据");
        }
        var request = new Request();
        Iterables.forEach(data,(index,line)->{
            if (index==0){
                /*首行：*/
                var msg=line.split(Server.BANK);
                request.setMethod(QuestMethodEnum.valueOf(msg[0]));
                var temp=msg[1];///start?x=10&y=20
                if (temp.contains("?")){
                    var temps=temp.split("\\?");
                    request.setRoute(temps[0]);
                    request.setParameter(temps[1]);
                }else {
                    request.setRoute(temp);
                }
                request.setServletInfo(request.getApplicationContext().getServletInfo(request.getRoute()));
                request.setQuestScheme(QuestScheme.get(msg[2]));
            }
        });
        return request;
    }
}
