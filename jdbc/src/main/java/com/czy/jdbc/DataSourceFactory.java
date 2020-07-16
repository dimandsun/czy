package com.czy.jdbc;

import com.czy.jdbc.exception.DataSourceException;
import com.czy.util.text.StringUtil;

import java.sql.SQLException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @author chenzy
 * @date 2020-07-16
 */
public class DataSourceFactory{
    private static Map<String, SimpleDataSource> dataSourceMap = Collections.synchronizedMap(new HashMap<>());
    private DataSourceFactory() {
    }
    public static SimpleDataSource getDataSource(String dataSourceKey) {
        return dataSourceMap.get(dataSourceKey);
    }

    public static void loadDirver(String dirverClassName) {
        try {
            Class.forName(dirverClassName);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    public static void createDataSource(String dataSourceKey, String url, String userName, String password) throws SQLException {
        if (StringUtil.isBlankOr(dataSourceKey,url,userName)){
            throw new DataSourceException("数据源创建失败");
        }
        if (dataSourceMap.containsKey(dataSourceKey)) {
            throw new DataSourceException("dataSourceKey重复");
        }
        dataSourceMap.put(dataSourceKey, new SimpleDataSource(url, userName, password));
    }

    public static void clear(){
        if (dataSourceMap.isEmpty()){
            return;
        }
        dataSourceMap.values().forEach(dataSource -> dataSource.clear());
    }
}
