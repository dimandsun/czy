package com.czy.core.util;

import com.czy.core.annotation.Table;
import com.czy.util.StringUtil;

/**
 * @author chenzy
 * @since 2020-04-28
 * @description
 */
public class TableUtil {
    private TableUtil(){

    }

    public static<Bean> String getTableName(Class<Bean> beanClass){
        String tableName = null;
        if (beanClass.isAnnotationPresent(Table.class)){
            tableName=(beanClass.getAnnotation(Table.class)).value();
        }
        if (StringUtil.isBlank(tableName)){
            tableName=StringUtil.lowFirst(beanClass.getSimpleName().replace(".class", ""));
        }
        return tableName;
    }
}
