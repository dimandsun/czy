package com.czy.util;

import com.czy.util.json.JsonUtil;
import net.sf.cglib.beans.BeanMap;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author chenzy
 * @since 2020-03-21
 */
public class BeanUtil {
    private BeanUtil() {
    }

    /**
     * bean=>json字符串
     * 但是把变量都设置为驼峰形式 doc_id转成docId
     *
     * @param model
     * @return
     */
    public static String model2Str(Object model) {
        return JsonUtil.model2Str(model2Map(model));
    }

    /**
     * bean=》map
     *
     * @param model
     * @return
     */
    public static <T> Map model2Map(T model) {
        BeanMap beanMap = BeanMap.create(model);
        var map = new HashMap<>(beanMap.size());
        for (Object key : beanMap.keySet()) {
            map.put(key + "", beanMap.get(key));
        }
        return map;
    }

    public static <T> T map2Model(Map map, T model) {
        BeanMap beanMap = BeanMap.create(model);
        beanMap.putAll(map);
        return model;
    }

    /**
     * 将map装换为javabean对象
     *
     * @param map
     * @param modelClass
     * @return
     */
    public static <T> T map2Model(Map map, Class<T> modelClass) {
        try {
            return map2Model(map,modelClass.getDeclaredConstructor().newInstance());
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
     * modelList=》mapList
     *
     * @param modelList
     * @param <T>
     * @return
     */
    public static <T> List<Map> modelToMap(List<T> modelList) {
        if (modelList == null || modelList.size() < 1) {
            return null;
        }
        List<Map> result = new ArrayList<>(modelList.size());
        for (T model : modelList) {
            result.add(model2Map(model));
        }
        return result;
    }

    /**
     * mapList=>modelList
     *
     * @param mapList
     * @param modelClass
     * @param <T>
     * @return
     */
    public static <T> List<T> map2Model(List<Map> mapList, Class<T> modelClass) {
        if (mapList == null || mapList.size() < 1) {
            return null;
        }
        List<T> result = new ArrayList<>(mapList.size());
        for (Map map : mapList) {
            result.add(map2Model(map, modelClass));
        }
        return result;
    }
}
