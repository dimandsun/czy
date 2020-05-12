package com.czy.util;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author chenzy
 * @description
 * @since 2020-05-12
 */
public class ObjectUtil {
    private ObjectUtil(){}
    /**
     * 生成对象集合：
     * 用反射，根据指定类型，生成对象集合，
     * 要求对应类型必须有 只有一个参数的构造方法，且这个参数是字符串类型
     */
    public static<T extends Object> List<T> getObjectList(Class<T> objClass, String... pars) {
        if (objClass==null||pars == null || pars.length == 0) {
            return null;
        }
        var objList = new ArrayList<T>(pars.length);
        for (String par : pars) {
            try {
                objList.add(objClass.getDeclaredConstructor(String.class).newInstance(par));
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
        }
        return objList;
    }
}
