package com.czy.util;

import net.sf.cglib.beans.BeanCopier;
import net.sf.cglib.beans.BeanMap;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;
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
    public static <From, To> To bean2Bean(From bean1,To to) {
        if (bean1==null||to==null){
            return null;
        }
        Class<To> toClass= (Class<To>) to.getClass();

        Class<From> fromClass= (Class<From>) bean1.getClass();
        String key = getKey(fromClass,toClass);
        BeanCopier beanCopier;
        if (COPIER_CACHE.containsKey(key)) {
            beanCopier = COPIER_CACHE.get(key);
        }else {
            beanCopier=BeanCopier.create(bean1.getClass(), toClass, false);
            COPIER_CACHE.put(key,beanCopier);
        }
        beanCopier.copy(bean1,to,null);
        return to;
    }
    public static <From, To> To bean2Bean(From bean1,Class<To> toClass) {
        if (bean1==null||toClass==null){
            return null;
        }
        try {
            To result = toClass.getDeclaredConstructor().newInstance();
            return bean2Bean(bean1,result);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return null;
    }
    /**
     * 生成key
     * @return string
     */
    private static<From, To> String getKey(Class<From> srcClazz, Class<To> tgtClazz) {
        return srcClazz.getName() + tgtClazz.getName();
    }
    public static <T> T map2Model(Map map, T model) {
        BeanMap beanMap = BeanMap.create(model);
        beanMap.putAll(map);
        return model;
    }
}
