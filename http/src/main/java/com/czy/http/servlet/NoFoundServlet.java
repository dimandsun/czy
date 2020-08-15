package com.czy.http.servlet;

import com.czy.http.enums.ResponseCode;
import com.czy.http.model.Request;
import com.czy.http.model.Response;

import java.nio.charset.Charset;

/**
 * @author chenzy
 * @date 2020-08-15
 */
public class NoFoundServlet extends Servlet {
    @Override
    protected void exec(Request request, Response response) {
        response.setResponseCode(ResponseCode.NotFound);
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
    <h1>未找到资源!</h1>
</body>
</html>
""";
        response.append(html);
    }
}
