package com.czy.http.factory;

import com.czy.http.ApplicationContext;
import com.czy.http.model.Request;

import java.nio.channels.ReadableByteChannel;

/**
 * @author chenzy
 * @date 2020-07-30
 */
public class RequestFactory {
    private RequestFactory() {
    }

    public static Request createRequest(ApplicationContext applicationContext, ReadableByteChannel wrappedChannel) {
        return null;
    }
}
