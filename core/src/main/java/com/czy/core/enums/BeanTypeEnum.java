package com.czy.core.enums;

import com.czy.util.enums.IEnum;

/**
 * @author chenzy
 * @date 2020-08-10
 * 从配置文件注入的bean：File
 * Bean注解的bean：Default
 * Config注解的bean：Config
 * Controller注解的bean：Controller
 * Dao注解的bean：Dao
 * Service注解的bean：Service
 */
public enum BeanTypeEnum implements IEnum<String> {
    Default,File,Config,Controller,Dao,Service;

    @Override
    public String getValue() {
        return name();
    }
}
