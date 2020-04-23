package com.czy.util;

/**
 * 枚举都要继承此接口，
 *
 * @param <Key> 枚举实际值的数据类型
 */
public interface IEnum<Key> {
    //枚举实际值
    Key getValue();

}
