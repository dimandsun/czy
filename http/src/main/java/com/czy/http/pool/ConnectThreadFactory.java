package com.czy.http.pool;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinWorkerThread;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author chenzy
 * @date 2020-08-15
 */
public class ConnectThreadFactory implements ForkJoinPool.ForkJoinWorkerThreadFactory {
    private final AtomicInteger mThreadNum = new AtomicInteger(1);
    public Thread newThread(Runnable r) {
        return new Thread(r, "连接线程-" + mThreadNum.getAndIncrement());
    }
    @Override
    public ForkJoinWorkerThread newThread(ForkJoinPool pool) {
        return new ConnectThread(pool);
    }
}
