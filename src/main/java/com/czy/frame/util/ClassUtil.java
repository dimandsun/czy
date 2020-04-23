package com.czy.frame.util;


/**
 * @author chenzy
 * @description
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

    public static void main(String[] args) {
        System.out.println(ClassUtil.isBasicDataType(Integer.class));
        System.out.println(ClassUtil.isBasicDataType(String.class));
    }
}
