package com.czy.jdbc.pool;

/**
 * @author chenzy
 * @date 2020-07-21
 *
 * maxConnectNum;//最大连接数
 * maxFreeConnectNum;//最大空闲连接数。也是最小连接数。连接池初始化时就会创建的连接。
 */
public record DataSourceSetting(String driverClassName,String dataSourceKey, String url, String userName, String password,int maxConnectNum,int maxFreeConnectNum) {}
