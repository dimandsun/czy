package com.czy.util;


import com.czy.core.annotation.Table;

/**
 * @author chenzy
 * @since 2020-04-28
 * @description
 */
public class TableUtil {
    private TableUtil(){

    }

    public static<Bean> String getTableName(Class<Bean> beanClass){
        return beanClass.isAnnotationPresent(Table.class) ? ((Table) beanClass.getAnnotation(Table.class)).value() :
                StringUtil.lowFirst(beanClass.getSimpleName().replace(".class", ""));
    }
}
