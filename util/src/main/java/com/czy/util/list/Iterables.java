package com.czy.util.list;

import com.czy.util.model.StringMap;

import java.util.Objects;
import java.util.function.BiConsumer;

/**
 * @author chenzy
 * @date 2020-07-29
 * Iterable 的工具类
 */
public class Iterables {
    private Iterables(){}

    /**
     * 带索引的遍历
     * @param elements
     * @param action
     * @param <E>
     */
    public static <E> void forEach(Iterable<? extends E> elements, BiConsumer<Integer, ? super E> action) {
        Objects.requireNonNull(elements);
        Objects.requireNonNull(action);
        int index = 0;
        for (E element : elements) {
            action.accept(index++, element);
        }

    }
}
