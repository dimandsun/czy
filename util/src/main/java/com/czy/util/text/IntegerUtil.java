package com.czy.util.text;

/**
 * @author chenzy
 * @date 2020-07-21
 */
public class IntegerUtil {
    private IntegerUtil(){}
    /**
     * 对象转Integer,null返回null
     *
     * @param value
     * @return
     */
    public static Integer getInt(Integer value, Integer defaultValue) {
        if (value == null) {
            return defaultValue;
        }
        if (value==0){
            return defaultValue;
        }
        return value==0?defaultValue:value;
    }
}
