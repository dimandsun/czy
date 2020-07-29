package com.czy.util.list;

import com.czy.util.model.StringMap;

import java.util.Enumeration;
import java.util.Map;
import java.util.Objects;

/**
 * @author chenzy
 * @date 2020-07-29
 */
public class EnumList<T> implements Enumeration<T> {
    private T[] datas;
    private int nextIndex=0;
    public EnumList(T[] datas) {
        if (datas==null||datas.length==0){
            this.datas= (T[]) new Object[0];
        }else {
            this.datas = datas;
        }
    }

    @Override
    public boolean hasMoreElements() {
        return nextIndex<datas.length;
    }

    @Override
    public T nextElement() {
        if (hasMoreElements()){
            return datas[nextIndex++];
        }
        return null;
    }
}
