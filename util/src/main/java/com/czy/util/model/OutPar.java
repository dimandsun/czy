package com.czy.util.model;

/**
 * java中没有out型参数，如果不用OutPar封装会在方法中更改的对象，则不利于维护代码，破坏代码的逻辑结构，所以out参数统一使用OutPar封装
 * @author chenzy
 * @since 2020.01.17
 */
public class OutPar<V> extends Par<V>{
    public OutPar(V v) {
        super(v);
    }

    public OutPar() {
        super();
    }
}
