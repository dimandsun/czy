package com.czy.jdbc;

import com.czy.jdbc.exception.ConnectionClosedException;
import com.czy.jdbc.exception.ConnectionNullException;

import javax.sql.DataSource;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

/**
 * 一个简单的DataSource实现
 *
 * @author leizhimin 2010-1-14 0:03:17
 */
public class SimpleDataSource implements DataSource {
    private String dirverClassName = "com.mysql.cj.jdbc.Driver";
    private String url = "jdbc:mysql://106.54.230.187:3306/czy?useUnicode=true&characterEncoding=utf-8&useSSL=false&serverTimezone=GMT&allowPublicKeyRetrievalx=true";
    private String userName = "root";
    private String password = "chen";
    //连接池
    private LinkedList<MyConnection> pool = (LinkedList<MyConnection>) Collections.synchronizedList(new LinkedList<MyConnection>());

    public SimpleDataSource(String url, String userName, String password) {
        this.url = url;
        this.userName = userName;
        this.password = password;
    }

    public synchronized Connection getConnection() throws SQLException {
        if (pool.isEmpty()) {
            addConnection();
        }
        var con = pool.pollLast();
        if (con == null) {
            throw new ConnectionNullException("连接池获取连接失败");
        }
        if (con.isClosed()) {
            con.isClosed(false);
        }
        var connection = con.get();
        if (connection.isClosed()) {
            throw new ConnectionClosedException("连接已关闭");
        }
        return connection;
    }
    private void addConnection() throws SQLException {
        var con = DriverManager.getConnection(url, userName, password);
        pool.addLast(new MyConnection(con, this));
    }
    /**
     * 连接归池
     * @param conn
     */
    public synchronized void close(MyConnection conn) {
        pool.addLast(conn);
    }

    /**
     * 释放资源
     */
    public synchronized void clear() {
        pool.clear();
    }

    public Connection getConnection(String username, String password) throws SQLException {
        return getConnection();
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
