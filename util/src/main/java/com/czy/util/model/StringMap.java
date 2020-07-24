package com.czy.util.model;

import java.util.Map;

/**
 * @author chenzy
 * 
 * @since 2020-03-09
 */
public class StringMap<Value extends Object> extends MyMap<String,Value> {

    public StringMap() {
        super();
    }

    public StringMap(int initialCapacity) {
        super(initialCapacity);
    }

    public StringMap(int initialCapacity, String s, Value value) {
        super(initialCapacity, s, value);
    }

    public StringMap(String s, Value value) {
        super(s, value);
    }

    public static StringMap translate(Map<String, Object> proMap) {
        var map=new StringMap<>();
        map.putAll(proMap);
        return map;
    }
    @Override
    public StringMap add(String s, Value value) {
         super.add(s, value);
         return this;
    }
}
