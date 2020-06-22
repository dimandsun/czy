package com.czy.util.model;

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

    @Override
    public StringMap add(String s, Value value) {
         super.add(s, value);
         return this;
    }
}
