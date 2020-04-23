package com.czy.frame.model;

import java.util.LinkedHashMap;

/**
 * @author chenzy
 * @description
 * @since 2020-03-09
 */
public class MyMap<T extends Object> extends LinkedHashMap<String,T> {
    public MyMap() {
    }

    public MyMap(int initialCapacity) {
        super(initialCapacity);
    }
    public MyMap(int initialCapacity, String key, T value) {
        super(initialCapacity);
        this.add(key,value);
    }
    public MyMap(String key, T value) {

        this.add(key,value);
    }

    public MyMap add(String key, T value) {
        super.put(key, value);
        return this;
    }

}
