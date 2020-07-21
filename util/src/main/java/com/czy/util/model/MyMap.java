package com.czy.util.model;

import com.czy.util.text.StringUtil;

import java.util.LinkedHashMap;

/**
 * @author chenzy
 * @since 2020-03-09
 * 继承LinkedHashMap
 * 不添加key或value为空的元素
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
        if (StringUtil.isBlankOr(key,value)){
            return this;
        }
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
