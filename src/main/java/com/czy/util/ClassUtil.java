package com.czy.util;


import java.lang.reflect.InvocationTargetException;
import java.util.List;

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

    /**
     * 对一系列的对象，分别执行指定的方法，
     *
     * @param objs       对象集合
     * @param methodName 方法名
     * @param pars       参数
     * @param <T>        对象类型
     * @return
     */
    public static <T extends Object> List<T> exexList(List<T> objs, String methodName, Object... pars) {
        if (StringUtil.isBlank(objs) || StringUtil.isBlank(methodName)) {
            return objs;
        }
        Class<T> c = (Class<T>) objs.get(0).getClass();
        Class[] parClasss = getClasses(pars);
        try {
            for (T o : objs) {
                var m=c.getMethod(methodName, parClasss);
                m.invoke(o,pars);
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }finally {
            return objs;
        }

    }

    /**
     * 返回类型数组，
     *
     * @param pars
     * @return
     */
    public static Class[] getClasses(Object[] pars) {
        if (pars != null && pars.length > 0) {
            Class[] parClasss = new Class[pars.length];
            for (int i = 0; i < pars.length; i++) {
                parClasss[i] = ClassUtil.getBasicType(pars[i].getClass());
            }
            return parClasss;
        }
        return null;
    }

    public static void main(String[] args) {
        System.out.println(ClassUtil.isBasicDataType(Integer.class));
        System.out.println(ClassUtil.isBasicDataType(String.class));
    }
}
