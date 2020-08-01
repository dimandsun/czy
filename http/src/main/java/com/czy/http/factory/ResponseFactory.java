package com.czy.http.factory;

import com.czy.http.ApplicationContext;
import com.czy.http.model.Request;
import com.czy.http.model.Response;

import java.io.OutputStream;
import java.util.Date;

import static com.czy.http.Server.BANK;
import static com.czy.http.Server.CRLF;

/**
 * @author chenzy
 * @date 2020-07-30
 */
public class ResponseFactory {
    private ResponseFactory() {
    }

    public static Response createResponse(Request request) {
        var response=new Response(request.getServletInfo());
        response.setQuestScheme(request.getQuestScheme());
        return response;
    }
}
