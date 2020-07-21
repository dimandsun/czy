package com.czy.jdbc;

import com.czy.jdbc.sql.SQLFactory;
import com.czy.log.LogFactory;
import com.czy.util.model.StringMap;
import com.czy.log.Log;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author chenzy
 * @date 2020-07-15
 */
public class JDBCUtil {
   private static Log log = LogFactory.getLog("sql");
   private JDBCUtil(){}
    /**
     * 新增一条记录
     * @param tableName
     * @param columnMap
     * @return
     */
   public static Boolean insertOne(String tableName, StringMap columnMap) {
        var insertSQL= SQLFactory.insert(tableName,columnMap);
        String sql = insertSQL.getEndPreSql();
        return executeOne(sql, insertSQL.getValues());
    }
    /**
     *  执行一个sql语句，修改
     */
    public static Boolean update(String tableName, StringMap setMap,StringMap whereMap) {
        var updateSQL= SQLFactory.update(tableName,setMap,whereMap);
        String sql = updateSQL.getEndPreSql();
        return executeOne(sql, updateSQL.getValues());
    }
    /**
     *  执行一个sql语句，删除
     */
    public static Boolean delete(String tableName,StringMap whereMap) {
        var updateSQL= SQLFactory.delete(tableName,whereMap);
        String sql = updateSQL.getEndPreSql();
        return executeOne(sql, updateSQL.getValues());
    }
    /**
     *  执行一个sql语句，查询
     */
    public static List<StringMap> select(String tableName,StringMap whereMap) {
        var selectSQL= SQLFactory.select(tableName,whereMap);
        String sql = selectSQL.getEndPreSql();
        return executeQuery(sql, selectSQL.getValues());
    }
    /**
     * 执行一个sql语句，增删改
     * @param sql 预处理语句
     * @param values 参数值
     * @return
     */
    private static Boolean executeOne(String sql, List values) {
        PreparedStatement ps=null;
        try (var con = DataSourceHolder.getInstance().get().getConnection()) {
            ps = con.prepareStatement(sql);
            for (int i = 1; i <= values.size(); i++) {
                ps.setObject(i, values.get(i));
            }
            int count = ps.executeUpdate();
            log.debug(ps.toString());
            //执行失败得到-1，返回false
            return -1 != count;
        } catch (SQLException e) {
            log.error(ps.toString());
            e.printStackTrace();
            if (ps!=null){
                System.out.println(ps.toString());
                try {
                    ps.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
            return false;
        }
    }
    private static List<StringMap> executeQuery(String sql, List values) {
        PreparedStatement ps=null;
        try (var con = DataSourceHolder.getInstance().get().getConnection()) {
            ps = con.prepareStatement(sql);
            for (int i = 1; i <= values.size(); i++) {
                ps.setObject(i, values.get(i));
            }
            var resultSet = ps.executeQuery();
            log.debug(ps.toString());
            var metaData=resultSet.getMetaData();
            var columnCount=metaData.getColumnCount();//列数
            var result = new ArrayList<StringMap>();
            while (resultSet.next()){
                var map = new StringMap(columnCount);
                /*下标从1开始*/
                for (int i = 1; i <= columnCount; i++) {
                    var columnName=metaData.getColumnName(i);
                    map.add(columnName,resultSet.getObject(i));
                }
                result.add(map);
            }
//            result.forEach(System.out::println);
            return result;
        } catch (SQLException e) {
            log.error(ps.toString());
            e.printStackTrace();
            if (ps!=null){
                System.out.println(ps.toString());
                try {
                    ps.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
            return null;
        }
    }



}
