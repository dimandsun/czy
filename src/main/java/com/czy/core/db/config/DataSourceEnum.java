package com.czy.core.db.config;


import com.czy.util.enums.IEnum;

import javax.sql.DataSource;

/**
 * 数据源枚举
 * @author chenzy
 * @date 2019.12.25
 */
public enum DataSourceEnum implements IEnum<String> {
    DEFAULT("mysql-czy-test",null),;
    private String beanName;
    private DataSource dataSource;

    public static DataSourceEnum getEnum(String beanName) {
        for (DataSourceEnum enumObj : DataSourceEnum.values()) {
            if (enumObj.beanName.equals(beanName)) {
                return enumObj;
            }
        }
        return null;
    }

    public DataSource getDataSource() {
        return dataSource;
    }

    public static DataSourceEnum setDataSource(String beanName, DataSource dataSource) {
        for (DataSourceEnum enumObj : DataSourceEnum.values()) {
            if (enumObj.beanName.equals(beanName)) {
                enumObj.dataSource=dataSource;
                return enumObj;
            }
        }
        return null;
    }

    DataSourceEnum(String beanName, DataSource dataSource) {
        this.beanName = beanName;
        this.dataSource = dataSource;
    }

    @Override
    public String getValue() {
        return beanName;
    }
}
