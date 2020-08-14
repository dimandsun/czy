package com.czy.util;

/**
 * @author chenzy
 * @date 2020-08-14
 */
public class ThreadUtil {
    private ThreadUtil() {
    }

    /*
     得到硬件线程数
    */
    public static Integer getThreadNum() {
        var threadNum = Runtime.getRuntime().availableProcessors();
        if (threadNum < 1) {
            threadNum = 1;
        }
        return threadNum;
    }
}
