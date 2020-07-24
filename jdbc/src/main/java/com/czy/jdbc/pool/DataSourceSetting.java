package com.czy.jdbc.pool;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author chenzy
 * @date 2020-07-21
 *
 * maxConnectNum;//最大连接数
 * maxFreeConnectNum;//最大空闲连接数。也是最小连接数。连接池初始化时就会创建的连接。
 */
public record DataSourceSetting(@JsonProperty("dataSourceKey") String dataSourceKey,@JsonProperty("driverClassName") String driverClassName,@JsonProperty("url") String url
        ,@JsonProperty("userName") String userName,@JsonProperty("password") String password,@JsonProperty("maxConnectNum") int maxConnectNum,@JsonProperty("maxFreeConnectNum") int maxFreeConnectNum) {


}
