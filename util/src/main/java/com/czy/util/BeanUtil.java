package com.czy.util;

import net.sf.cglib.beans.BeanCopier;

import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author chenzy
 * @since 2020-07-10
 */
public class BeanUtil {
    /**
     * BeanCopier的缓存
     */
    static final ConcurrentHashMap<String, BeanCopier> COPIER_CACHE = new ConcurrentHashMap<>();

    private BeanUtil() {
    }
    public static void  clear(){
        COPIER_CACHE.clear();
    }
    public static <From, To> To bean2Bean(From bean1,Class<To> toClass) {
        if (bean1==null||toClass==null){
            return null;
        }
        Class<From> fromClass= (Class<From>) bean1.getClass();
        String key = getKey(fromClass,toClass);
        BeanCopier beanCopier;
        if (COPIER_CACHE.containsKey(key)) {
            beanCopier = COPIER_CACHE.get(key);
        }else {
            beanCopier=BeanCopier.create(bean1.getClass(), toClass, false);
            COPIER_CACHE.put(key,beanCopier);
        }
        To result = null;
        try {
            result = toClass.getDeclaredConstructor().newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        beanCopier.copy(bean1,result,null);
        return result;
    }
    /**
     * 生成key
     * @return string
     */
    private static<From, To> String getKey(Class<From> srcClazz, Class<To> tgtClazz) {
        return srcClazz.getName() + tgtClazz.getName();
    }
}
