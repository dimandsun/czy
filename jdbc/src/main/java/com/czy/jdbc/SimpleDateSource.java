package com.czy.jdbc;

import javax.sql.DataSource;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.logging.Logger;

/**
 * 一个简单的DataSource实现
 *
 * @author leizhimin 2010-1-14 0:03:17
 */
public class SimpleDateSource implements DataSource {
    private String dirverClassName = "com.mysql.cj.jdbc.Driver";
    private String url = "jdbc:mysql://106.54.230.187:3306/czy?useUnicode=true&characterEncoding=utf-8&useSSL=false&serverTimezone=GMT&allowPublicKeyRetrievalx=true";
    private String userName = "root";
    private String password = "chen";
    //连接池
    private static LinkedList<Connection> pool = (LinkedList<Connection>) Collections.synchronizedList(new LinkedList<Connection>());
    private static SimpleDateSource instance = new SimpleDateSource();
    private SimpleDateSource() {
    }
    public static SimpleDateSource getInstance(String dirverClassName, String url, String userName, String password) {
        try {
            Class.forName(instance.dirverClassName);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        instance.dirverClassName = dirverClassName;
        instance.url = url;
        instance.userName = userName;
        instance.password = password;
        return instance;
    }
    public Connection getConnection() throws SQLException {
        synchronized (pool) {
            if (pool.size() > 0) return pool.removeFirst();
            else return makeConnection();
        }
    }

    /**
     * 连接归池
     * @param conn
     */
    public static void freeConnection(Connection conn) {
        pool.addLast(conn);
    }

    private Connection makeConnection() throws SQLException {
        return DriverManager.getConnection(url, userName, password);
    }

    public Connection getConnection(String username, String password) throws SQLException {
        return DriverManager.getConnection(url, username, password);
    }

    public PrintWriter getLogWriter() throws SQLException {
        return null;
    }

    public void setLogWriter(PrintWriter out) throws SQLException {

    }

    public void setLoginTimeout(int seconds) throws SQLException {

    }

    public int getLoginTimeout() throws SQLException {
        return 0;
    }

    @Override
    public Logger getParentLogger() throws SQLFeatureNotSupportedException {
        return null;
    }


    public <T> T unwrap(Class<T> iface) throws SQLException {
        return null;
    }

    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        return false;
    }
}
