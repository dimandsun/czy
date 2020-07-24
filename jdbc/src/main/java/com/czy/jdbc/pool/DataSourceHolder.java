package com.czy.jdbc.pool;

import javax.sql.DataSource;

/**
 * @author chenzy
 * @date 2020-07-16
 * 初始化时应设置默认数据源
 */
public class DataSourceHolder {
    private final ThreadLocal<String> curDataSource = new ThreadLocal<>();
    private final static DataSourceHolder instance=new DataSourceHolder();
    public static DataSourceHolder getInstance() {
        return instance;
    }
    private DataSourceHolder() {
    }
    private String defaultKey;
    public String getDefaultKey() {
        return defaultKey;
    }

    public void setDefaultKey(String defaultKey) {
        this.defaultKey = defaultKey;
    }
    /**
     *  设置数据源
     */
    public void set(String dataSourceKey) {
        curDataSource.set(dataSourceKey);
    }

    /**
     *  获取数据源
     */
    public DataSource get() {
        if (curDataSource.get()==null){
            set(defaultKey);
        }
        var dataSourceKey=curDataSource.get();
        DataSource dataSource= DataSourceFactory.getDataSource(dataSourceKey);
        return dataSource;
    }
    /**
     *  清除数据源
     */
    public void clear() {
        curDataSource.remove();
    }
}
