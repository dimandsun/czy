package com.czy.http.servlet;

import com.czy.http.model.Request;
import com.czy.http.model.Response;
import com.czy.http.model.ServletInfo;

import java.nio.charset.Charset;

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
        response.setCharSet(Charset.forName("UTF-8"));
        var html= """
<!DOCTYPE html><html>
<html>
<head>
    <meta charset="UTF-8" />
    <title></title>
</head>
<body bgcolor="white">
    <h1>欢迎页!</h1>
</body>
</html>
""";
        response.append(html);
    }
}
