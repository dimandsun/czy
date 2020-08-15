package com.czy.http.pool;

/**
 * @author chenzy
 * @date 2020-08-15
 */
public class ConnectHandler implements Thread.UncaughtExceptionHandler {
    @Override
    public void uncaughtException(Thread t, Throwable e) {
        System.err.println(t.getName() + " 线程异常！");
        e.printStackTrace();
    }
}

