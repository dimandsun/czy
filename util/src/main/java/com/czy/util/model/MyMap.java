package com.czy.util.model;

import java.util.LinkedHashMap;

/**
 * @author chenzy
 * @description
 * @since 2020-03-09
 */
public class MyMap<Key,Value extends Object> extends LinkedHashMap<Key,Value> {
    public MyMap() {
    }

    public MyMap(int initialCapacity) {
        super(initialCapacity);
    }
    public MyMap(int initialCapacity, Key key, Value value) {
        super(initialCapacity);
        this.add(key,value);
    }
    public MyMap(Key key, Value value) {

        this.add(key,value);
    }

    public MyMap add(Key key, Value value) {
        super.put(key, value);
        return this;
    }

    public Value get(Key key, Value defaultValue) {
        Value value =this.get(key);
        if (value==null){
            value=defaultValue;
            this.put(key,value);
        }
        return value;
    }
}
