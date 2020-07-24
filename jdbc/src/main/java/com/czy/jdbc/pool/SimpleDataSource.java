package com.czy.jdbc.pool;

import com.czy.jdbc.exception.ConnectionClosedException;
import com.czy.jdbc.exception.ConnectionNullException;
import com.czy.jdbc.exception.DataSourceException;
import com.czy.util.text.IntegerUtil;

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
    private String driverClassName = "com.mysql.cj.jdbc.Driver";
    private String url = "jdbc:mysql://106.54.230.187:3306/czy?useUnicode=true&characterEncoding=utf-8&useSSL=false&serverTimezone=GMT&allowPublicKeyRetrievalx=true";
    private String userName = "root";
    private String password = "chen";
    private int maxConnectNum;//最大连接数
    private int maxFreeConnectNum;//最大空闲连接数。也是最小连接数。连接池初始化时就会创建的连接。
    private int freeConnectNum;//当前空闲连接数。
    private int curConnectNum;//当前连接数 包括空闲连接和在使用连接

    //连接池
    private List<MyConnection> pool =  Collections.synchronizedList(new LinkedList<>());

    public SimpleDataSource(DataSourceSetting sourceSetting) throws SQLException {
        this.url = sourceSetting.url();
        this.userName = sourceSetting.userName();
        this.password = sourceSetting.password();
        setMaxConnectNum(sourceSetting.maxConnectNum());
        setMaxFreeConnectNum(sourceSetting.maxFreeConnectNum());
        for (int i = 0; i < maxFreeConnectNum; i++) {
            addConnection();
        }
    }

    public synchronized Connection getConnection() throws SQLException {
        if (freeConnectNum<0){
            throw new DataSourceException("连接池获取连接异常：当前空闲连接数freeConnectNum为"+freeConnectNum);
        }else if (freeConnectNum==0) {
            addConnection();
        }
        var con = pool.remove(0);
        if (con == null) {
            throw new ConnectionNullException("连接池获取连接失败");
        }
        freeConnectNum--;
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
        if (curConnectNum>maxConnectNum){
            throw new DataSourceException("连接池异常：连接数curConnectNum-"+curConnectNum+"大于最大值maxConnectNum-"+maxConnectNum);
        }else if (curConnectNum==maxConnectNum){
            throw new DataSourceException("连接池异常：连接数已经到达最大值："+curConnectNum);
        }
        var con = DriverManager.getConnection(url, userName, password);
        pool.add(new MyConnection(con, this));
        freeConnectNum++;
        curConnectNum++;
    }
    /**
     * 连接归池
     * @param conn
     */
    public synchronized void close(MyConnection conn) throws SQLException  {
        if (freeConnectNum>maxFreeConnectNum){
            throw new DataSourceException("连接池异常：当前空闲连接数freeConnectNum-"+freeConnectNum+"大于最大空闲连接数maxFreeConnectNum-"+maxFreeConnectNum);
        }else if (freeConnectNum==maxFreeConnectNum){
            conn.realClose();
            freeConnectNum--;
            curConnectNum--;
        }else {
            pool.add(conn);
            freeConnectNum++;
        }

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
    private void setMaxConnectNum(int maxConnectNum) {
        this.maxConnectNum = IntegerUtil.getInt(maxConnectNum,30);
    }

    private void setMaxFreeConnectNum(int maxFreeConnectNum) {
        this.maxFreeConnectNum = IntegerUtil.getInt(maxFreeConnectNum,10);
    }

    private void setFreeConnectNum(int freeConnectNum) {
        this.freeConnectNum = IntegerUtil.getInt(freeConnectNum,5);
    }

    private void setCurConnectNum(int curConnectNum) {
        this.curConnectNum = IntegerUtil.getInt(curConnectNum,10);
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
