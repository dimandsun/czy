package com.czy.util;


import com.czy.util.text.StringUtil;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * @author chenzy
 * 
 * @since 2020-04-08
 */
public class ClassUtil {
    private ClassUtil() {

    }

    public static Boolean isBasicDataType(Class clazz) {
        /*判断是否是基本数据类型*/
        if (clazz.isPrimitive()) {
            return true;
        }
        /*是否是字符串*/
        if (clazz == String.class) {
            return true;
        }
        /*判断是否是基本数据类型的包装类*/
        try {
            return ((Class) clazz.getField("TYPE").get(null)).isPrimitive();
        } catch (IllegalAccessException e) {
            return false;
        } catch (NoSuchFieldException e) {
            return false;
        }
    }

    /**
     * 如果是基本数据类型的包装类，则返回基本数据类型，其他则返回原类型
     *
     * @param c
     * @return
     */
    public static Class getBasicType(Class c) {
        try {
            return (Class) c.getField("TYPE").get(null);
        } catch (IllegalAccessException e) {
            return c;
        } catch (NoSuchFieldException e) {
            return c;
        }
    }
    public static void main(String[] args) {
        System.out.println(ClassUtil.isBasicDataType(Integer.class));
        System.out.println(ClassUtil.isBasicDataType(String.class));
    }
}
