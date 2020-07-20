package com.czy.jdbc;

import com.czy.log.LogFactory;
import com.czy.util.model.StringMap;
import com.czy.log.Log;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

/**
 * @author chenzy
 * @date 2020-07-15
 */
public class JDBCUtil {
    Log log = LogFactory.getLog("sql");
    /**
     * 执行一个sql语句，增删改
     *
     * @param tableName
     * @param columnMap
     * @return
     */
    Boolean insertOne(String tableName, StringMap columnMap) {
        var sqlBuilder=SQLBuilder.insert(tableName).column(columnMap);
        String sql = sqlBuilder.preSql();
        return executeOne(sql, sqlBuilder.preValues());
    }

    Boolean executeOne(String sql, List values) {
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

}
