package com.czy.util.model;

/**
 * @author chenzy
 * @since 2020-04-03
 *  存储数据的对象，
 */
public class Par<V> {
    private V v;

    public V get() {
        return v;
    }

    public void set(V v) {
        this.v = v;
    }

    @Override
    public String toString() {
        if (v==null){
            return "";
        }
        return v.toString();
    }

    public Par() {
    }

    public Par(V v) {
        this.v = v;
    }
}
