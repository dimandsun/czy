package com.czy.util.list;

import java.lang.reflect.Array;
import java.util.Enumeration;
import java.util.Locale;
import java.util.Map;

/**
 * @author chenzy
 * @date 2020-07-29
 */
public class EnumerationFactory {
    private EnumerationFactory() {
    }
    public static<T> Enumeration<String> createEnumerationKey(Map<String,T> map){
        String[] datas;
        if (map==null||map.isEmpty()){
            datas=new String[0];
        }else {
            var dataSet=map.keySet();
            datas=new String[dataSet.size()];
            Iterables.forEach(dataSet,((index, key) -> datas[index]=key));
        }
        return new EnumList(datas);
    }
    public static <T> Enumeration<T> createEnumerationValue(Map<String,T> map){
        T[] datas;
        if (map==null||map.isEmpty()){
            datas= (T[]) new Object[0];
        }else {
            var dataSet=map.values();
            datas= (T[]) Array.newInstance(map.get(0).getClass(),dataSet.size());
            Iterables.forEach(dataSet,((index, key) -> datas[index]=key));
        }
        return new EnumList(datas);
    }
    public static <T> Enumeration<T> createEnumeration(T[] datas){
        return new EnumList(datas);
    }
}
