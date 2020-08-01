package httpServer;

import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.net.URI;

/**
 * @author chenzy
 * @date 2020-08-01
 */
public class StartHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        System.out.println("getProtocol:" + exchange.getProtocol());//协议版本
        System.out.println("getRequestMethod:" + exchange.getRequestMethod());//请求方法GET、POST
        System.out.println("getResponseCode:" + exchange.getResponseCode());//返回已经设置的响应code，还没设置返回-1

        HttpContext context = exchange.getHttpContext();
        System.out.println("context.getPath:" + context.getPath());
        System.out.println("context.toString:" + context.toString());
        System.out.println("context.getAttributes：" + context.getAttributes());
        System.out.println("context.getAuthenticator：" + context.getAuthenticator());
        System.out.println("context.getFilters：" + context.getFilters());
        System.out.println("context.getHandler：" + context.getHandler());
        System.out.println("context.getServer：" + context.getServer());

        System.out.println("getLocalAddress:" + exchange.getLocalAddress());
        System.out.println("getPrincipal:" + exchange.getPrincipal());
        System.out.println("getRemoteAddress:" + exchange.getRemoteAddress());

        URI uri = exchange.getRequestURI();
        System.out.println("getRequestURI:" + exchange.getRequestURI());
        System.out.println("uri.getAuthority:" + uri.getAuthority());
        System.out.println("uri.getFragment:" + uri.getFragment());
        System.out.println("uri.getHost:" + uri.getHost());
        System.out.println("uri.getPath:" + uri.getPath());
        System.out.println("uri.getQuery:" + uri.getQuery());//url里get请求的参数
        System.out.println("uri.getRawAuthority:" + uri.getRawAuthority());
        System.out.println("uri.getRawFragment:" + uri.getRawFragment());
        System.out.println("uri.getRawPath:" + uri.getRawPath());
        System.out.println("uri.getRawQuery:" + uri.getRawQuery());
        System.out.println("uri.getRawSchemeSpecificPart:" + uri.getRawSchemeSpecificPart());
        System.out.println("uri.getRawUserInfo:" + uri.getRawUserInfo());
        System.out.println("uri.getScheme:" + uri.getScheme());
        System.out.println("uri.getSchemeSpecificPart:" + uri.getSchemeSpecificPart());
        System.out.println("uri.getUserInfo:" + uri.getUserInfo());
        System.out.println("uri.getPort:" + uri.getPort());

        System.out.println("getRequestBody:" + exchange.getRequestBody());


        exchange.sendResponseHeaders(200, 0);//len>0：响应体必须发送指定长度；len=0：可发送任意长度，关闭OutputStream即可停止；len=-1：不会发响应体；
        System.out.println("getResponseBody:" + exchange.getResponseBody());
        System.out.println("getResponseHeaders:" + exchange.getResponseHeaders());
        System.out.println("ResponseCode:" + exchange.getResponseCode());
        exchange.close();//先关闭打开的InputStream，再关闭打开的OutputStream
    }
}
