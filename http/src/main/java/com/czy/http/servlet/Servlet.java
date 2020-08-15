package com.czy.http.servlet;

import com.czy.http.model.Request;
import com.czy.http.model.Response;
import com.czy.http.model.ServletInfo;

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
            case POST -> doPost(request,response);
            case PUT -> doPut(request,response);
            case DELETE -> doDelete(request,response);
            default -> doGet(request,response);
        }
    }




}
