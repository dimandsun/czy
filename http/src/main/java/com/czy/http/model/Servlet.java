package com.czy.http.model;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author chenzy
 * @date 2020-07-30
 */
public abstract class Servlet {
    public void init(ServletInfo servletInfo){

    }
    public void doGet(Request request, Response response){
        exec(request,response);
    }
    public void doPost(Request request, Response response) {
        exec(request,response);
    }
    public void doDelete(Request request, Response response) {
        exec(request,response);
    }
    public void doPut(Request request, Response response) {
        exec(request,response);
    }
    protected void exec(Request request, Response response){

    }
    public void service(Request request, Response response){
        switch (request.getMethod()){
            case Post -> doPost(request,response);
            case Put -> doPut(request,response);
            case Delete -> doDelete(request,response);
            default -> doGet(request,response);
        }
    }




}
