package com.czy.jdbc;

import com.czy.jdbc.exception.SQLParseException;
import com.czy.jdbc.sql.SQLFactory;
import com.czy.log.Log;
import com.czy.log.LogFactory;
import com.czy.util.model.StringMap;

import java.sql.SQLException;
import java.util.List;

/**
 * @author chenzy
 * @date 2020-07-15
 */
public class JDBCUtil {
    private static Log log = LogFactory.getLog("sql");
    private JDBCUtil() {
    }

    /**
     * 新增一条记录
     *
     * @param tableName
     * @param columnMap
     * @return
     */
    public static Boolean insertOne(String tableName, StringMap columnMap) {
        try {
            Object result = SQLFactory.insert(tableName, columnMap).exec();
            System.out.println(result);
            return true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }

    }

    /**
     * 执行一个sql语句，修改
     */
    public static Boolean update(String tableName, StringMap setMap, StringMap whereMap) {
        try {
            Object result = SQLFactory.update(tableName, setMap, whereMap).exec();
            System.out.println(result);
            return true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }
    }

    /**
     * 执行一个sql语句，删除
     */
    public static Boolean delete(String tableName, StringMap whereMap) {
        try {
            Object result = SQLFactory.delete(tableName, whereMap).exec();
            System.out.println(result);
            return true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }
    }

    /**
     * 执行一个sql语句，查询
     */
    public static List<StringMap> select(String tableName, StringMap whereMap) {
        try {
            Object result = SQLFactory.select(tableName, whereMap).exec();
            System.out.println(result);
            return (List<StringMap>) result;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return null;
        }
    }
}
