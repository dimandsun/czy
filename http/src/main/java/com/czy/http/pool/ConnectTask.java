package com.czy.http.pool;

import com.czy.http.ApplicationContext;
import com.czy.http.factory.RequestFactory;
import com.czy.http.factory.ResponseFactory;
import com.czy.log.Log;
import com.czy.log.LogFactory;
import com.czy.util.io.NIOUtil;

import java.nio.channels.SocketChannel;
import java.util.concurrent.Callable;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author chenzy
 * @date 2020-08-15
 * 处理连接
 */
public class ConnectTask implements Callable<Boolean> {
    private static Log log = LogFactory.getLog("server");
    private SocketChannel connect;
    public ConnectTask(SocketChannel connect) {
        this.connect = connect;
    }
    @Override
    public Boolean call() throws Exception {
        if (!connect.isConnected()) {
            log.error("连接已关闭");
            return false;
        }
        /*创建request、response对象*/
        var request = RequestFactory.createRequest(connect);
        var response = ResponseFactory.createResponse(request);
        /*servlet执行业务方法*/
        var servlet = ApplicationContext.instance().getServlet(request.getRoute());
        servlet.service(request, response);
        //返回数据给客户端
        response.beforeReturn();
        connect.write(response.getCharSet().encode(response.getResult()));
        var file=response.getFile();
        NIOUtil.write(file, connect);
        log.debug("处理请求：{}:参数[{}]",request.getRoute(),request.getParameterMap());
//        log.debug("响应数据data:{}file:{}",response.getBody(),file==null?"":file.getName())
        //连接关闭
        connect.shutdownInput();
        connect.shutdownOutput();
        connect.close();
        return true;
    }
}
