package com.czy.util.set;

import com.czy.util.model.StringMap;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @author chenzy
 * @since 2020/7/24
 */
public class MapUtil {
    private MapUtil(){}

    /**
     * 对多层map取值。当然，keys长度为1时，是对单层map取值
     * @param map
     * @param keys
     * @return
     */
    public static<K,V> Optional<V> getValue(HashMap<K,Object> map, K... keys) {
        if (keys==null){
            return Optional.empty();
        }
        if (keys.length==1){
            return Optional.ofNullable((V) map.get(keys[0]));
        }
        Map<K, Object> mapTemp =map;
        Object result=null;
        for (K key:keys) {
            result=mapTemp.get(key);
            if (Map.class.isAssignableFrom(result.getClass())){
                mapTemp= (Map<K, Object>) result;
            }
        }
        return Optional.ofNullable((V) result);
    }
}
