package com.czy.util.model;

import java.util.ArrayList;
import java.util.List;

/**
 * @author chenzy
 * 
 * @since 2020-04-08
 */
public class MyList<T> extends ArrayList<T> {
    public MyList(int initialCapacity) {
        super(initialCapacity);
    }
    public MyList() {
    }

    public MyList addValue(T value){
        this.add(value);
        return this;
    }
    public MyList addValue(List<T> valueList){
        this.addAll(valueList);
        return this;
    }

}
