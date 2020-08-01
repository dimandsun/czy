package com.czy.http;

import com.czy.http.enums.ResponseCode;
import com.czy.http.model.Request;
import com.czy.http.model.Response;
import com.czy.http.model.Servlet;

/**
 * @author chenzy
 * @date 2020-08-01
 * 找不到资源时调用
 */
public class DefaultServlet extends Servlet {
    @Override
    protected void exec(Request request, Response response) {
        response.setResponseCode(ResponseCode.NotFound);
        response.setContentType("text/html");
        response.setCharSet("UTF-8");
        response.appendLine("<!DOCTYPE html><html>");
        response.appendLine("<head>");
        response.appendLine("<meta charset=\"UTF-8\" />");
        response.appendLine("<title></title>");
        response.appendLine("</head>");
        response.appendLine("<body bgcolor=\"white\">");
        response.appendLine("<h1>未找到资源</h1>");
        response.appendLine("</body>");
        response.appendLine("</html>");
    }
}
