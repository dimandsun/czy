package com.czy.jdbc;

import javax.sql.DataSource;

/**
 * @author chenzy
 * @date 2020-07-15
 */
public class JDBCConnectionPool {
    private static JDBCConnectionPool instance = new JDBCConnectionPool();
    private JDBCConnectionPool() {
    }
    public static JDBCConnectionPool getInstance(){
        return instance;

    }
    private String url = null;
    private String driverClass = null;
    private String user = null;
    private String password = null;
    private int maxConectNum = 0;//最大连接数
    private int maxFreeConectNum = 0;//最大空闲连接数。也是最小连接数。连接池初始化时就会创建的连接。
    private int freeConectNum = 0;//当前空闲连接数。
    private int conectNum = 0;//当前连接数
}
