package com.czy.http.servlet;

import com.czy.http.annotation.WebServlet;
import com.czy.http.enums.ResponseCode;
import com.czy.http.model.Request;
import com.czy.http.model.Response;

import java.nio.charset.Charset;

/**
 * @author chenzy
 * @date 2020-08-01
 * 找不到资源时调用
 */
@WebServlet(value="/default",name="default")
public class DefaultServlet extends Servlet {
    @Override
    protected void exec(Request request, Response response) {
        response.setResponseCode(ResponseCode.NotFound);
        response.setContentType("text/html");
        response.setCharSet(Charset.forName("UTF-8"));
        response.appendLine("<!DOCTYPE html><html>");
        response.appendLine("<head>");
        response.appendLine("<meta charset=\"UTF-8\" />");
        response.appendLine("<title></title>");
        response.appendLine("</head>");
        response.appendLine("<body bgcolor=\"white\">");
        response.appendLine("<h1>默认页！</h1>");
        response.appendLine("</body>");
        response.appendLine("</html>");
    }
}
