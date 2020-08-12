package com.czy.http;

import com.czy.http.enums.ResponseCode;
import com.czy.http.model.Request;
import com.czy.http.model.Response;
import com.czy.http.model.Servlet;
import com.czy.http.model.ServletInfo;
import com.czy.util.io.FileUtil;

/**
 * @author chenzy
 * @since 2020/6/26
 */
public class ResourceServlet extends Servlet {
    @Override
    public void init(ServletInfo servletInfo) {
        super.init(servletInfo);
    }
    protected void exec(Request request, Response response){
        var file= FileUtil.getResourceFile(request.getRoute());
        if (file==null||!file.exists()){
            response.setResponseCode(ResponseCode.NotFound);
        }
        response.setContentType("text/html");
        response.setCharSet("UTF-8");
        response.setFile(file);
    }
}
