package com.czy.util.list;

import com.czy.util.text.StringUtil;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

/**
 * @author chenzy
 *  集合工具类
 * @since 2020-05-11
 */
public class ListUtil {
    public static boolean isEmpty(Object[] array) {
        return (array == null || array.length == 0);
    }
    public static <T> boolean isEmpty(List<T> list) {
        return list == null || list.isEmpty();
    }

    public static <T> boolean isNotEmpty(List<T> list) {
return !isEmpty(list);
    }

    @SuppressWarnings("unchecked")
    public static <T> T[] list2Array(List<T> list) {
        if (isEmpty(list)){
            return null;
        }
        T[] ts= (T[]) list.toArray();
        return ts;
    }
    /*创建泛型数组*/
    @SuppressWarnings("unchecked")
    public static<T> T[] newArray(Class<T> tClass,int length){
        return (T[]) Array.newInstance(tClass, length);
    }
}
