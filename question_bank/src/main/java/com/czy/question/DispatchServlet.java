package com.czy.question;

import com.czy.core.dispatch.Dispatch;
import com.czy.core.dispatch.Quest;
import com.czy.http.model.Request;
import com.czy.http.model.Response;
import com.czy.http.servlet.DefaultServlet;

/**
 * @author chenzy
 * @date 2020-08-21
 */
public class DispatchServlet extends DefaultServlet {
    @Override
    protected void exec(Request request, Response response) {
        Object result=Dispatch.getInstance().exec(new Quest(request.getMethod(),request.getRoute(),request.getParameterMap()));
        response.append(result);
    }
}
