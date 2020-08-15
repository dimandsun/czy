package com.czy.http.pool;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinWorkerThread;

/**
 * @author chenzy
 * @date 2020-08-15
 */
public class ConnectThread extends ForkJoinWorkerThread {
    protected ConnectThread(ForkJoinPool pool) {
        super(pool);
    }

}
