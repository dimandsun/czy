package com.czy.frame.db.config;

/**
 * 设置当前线程的变量的工具类，用于设置对应的数据源名称
 * @author chenzy
 * @date 2019.12.25
 */
public class DataSourceHolder<DataSourceKey> {

    private Class<DataSourceKey> dataSourceKeyClass;
    private DataSourceKey defaultDataSourceKey;
    private final ThreadLocal<DataSourceKey> curDataSource = new ThreadLocal<>();
    private static DataSourceHolder instance;
    public static<DataSourceKey> DataSourceHolder<DataSourceKey> getInstance() {
        return instance;
    }
    public static<DataSourceKey> void createInstance(Class<DataSourceKey> dataSourceKeyClass, DataSourceKey defaultDataSourceKey) {
        instance = new DataSourceHolder(dataSourceKeyClass,defaultDataSourceKey);
    }
    protected DataSourceHolder(Class<DataSourceKey> dataSourceKeyClass, DataSourceKey defaultDataSourceKey) {
        this.dataSourceKeyClass = dataSourceKeyClass;
        this.defaultDataSourceKey = defaultDataSourceKey;
    }

    /**
     * @description 设置数据源
     */
    public void set(DataSourceKey dataSourceKey) {
        curDataSource.set(dataSourceKey);
    }

    /**
     * @description 获取数据源
     */
    public DataSourceKey get() {
        if (curDataSource.get()==null){
            set(defaultDataSourceKey);
        }
        return curDataSource.get();
    }
    /**
     * @description 清除数据源
     */
    public void clear() {
        curDataSource.remove();
    }

}
