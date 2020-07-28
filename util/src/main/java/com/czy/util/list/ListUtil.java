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
    /**
     * 对集合进行排序，集合的子对象需实现Comparable接口，
     * 注意：String没有实现Comparable
     * list.sort(T::compareTo);可实现此功能，此方法只用于学习stream().后续删除
     *
     * @param list
     * @param <T>  需实现Comparable接口
     * @return 排序结果
     */
    public static <T extends Comparable> List<T> sortList(List<T> list) {
        if (StringUtil.isBlank(list)) {
            return list;
        }

        Class<T> tClass = (Class<T>) list.get(0).getClass();
        T[] result = list.stream().sorted(T::compareTo)
                .toArray((int value) -> (T[]) Array.newInstance(tClass, list.size()));
        return Arrays.asList(result);
    }
    public static boolean isEmpty(Object[] array) {
        return (array == null || array.length == 0);
    }
    public static <T> boolean isEmpty(List<T> list) {
        return list == null || list.isEmpty();
    }

    public static <T> boolean isNotEmpty(List<T> list) {
return !isEmpty(list);
    }

    public static <T> T[] list2Array(List<T> list) {
        if (isEmpty(list)){
            return null;
        }
        T[] ts= (T[]) list.toArray();
        return ts;
    }
}
