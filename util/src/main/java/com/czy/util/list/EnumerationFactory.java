package com.czy.util.list;

import java.lang.reflect.Array;
import java.util.*;

/**
 * @author chenzy
 * @date 2020-07-29
 */
public class EnumerationFactory {
    private EnumerationFactory() {
    }

    public static Enumeration<String> createEnumerationKey(Map<String, ?> map) {
        String[] datas;
        if (map == null || map.isEmpty()) {
            datas = new String[0];
        } else {
            var dataSet = map.keySet();
            datas = new String[dataSet.size()];
            Iterables.forEach(dataSet, ((index, key) -> datas[index] = key));
        }
        return new EnumList<>(datas);
    }
    public static <T> Enumeration<T> createEnumeration(T[] datas) {
        return new EnumList<>(datas);
    }
}
