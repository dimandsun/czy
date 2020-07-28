package com.czy.httpcontainer;

/**
 * @author chenzy
 * @date 2020-07-28
 */
public interface Loader {
    ClassLoader getClassLoader();

    void setClassLoader(ClassLoader classLoader);
}
