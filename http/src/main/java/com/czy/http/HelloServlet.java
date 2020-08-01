package com.czy.http;

import com.czy.http.model.Request;
import com.czy.http.model.Response;
import com.czy.http.model.Servlet;
import com.czy.http.model.ServletInfo;
/**
 * @author chenzy
 * @since 2020/6/26
 */
public class HelloServlet extends Servlet {
    @Override
    public void init(ServletInfo servletInfo) {
        super.init(servletInfo);
    }

    @Override
    protected void exec(Request req, Response response) {
        response.setContentType("text/html");
        response.setCharSet("UTF-8");
        response.appendLine("<!DOCTYPE html><html>");
        response.appendLine("<head>");
        response.appendLine("<meta charset=\"UTF-8\" />");
        response.appendLine("<title></title>");
        response.appendLine("</head>");
        response.appendLine("<body bgcolor=\"white\">");
        response.appendLine("<h1>我的服务器!</h1>");
        response.appendLine("</body>");
        response.appendLine("</html>");
    }
}
