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
    protected void doGet(HttpServletRequest req, HttpServletResponse response) throws IOException{

    }

    public <T>T service(Request request, Response response){
        return null;
    }
}
