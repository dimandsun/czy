package com.czy.util;

/**
 * @author chenzy
 * 
 * @since 2020-05-25
 */
public class IntUtil {
    private IntUtil(){

    }

    /**
     * 是否在指定区域，大于等于最小值，小于最大值
     * @param target
     * @param min
     * @param max
     * @return
     */
    public static Boolean in(Integer target,Integer min,Integer max){
        if (target==null||(min==null&&max==null)){
            return false;
        }
        if (min==null){
            return target<max;
        }
        if (max==null){
            return target>=min;
        }
        return target>=min&&target<max;
    }
}
