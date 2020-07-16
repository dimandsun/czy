package com.czy.jdbc;

import com.czy.util.model.StringMap;

import java.sql.PreparedStatement;
import java.sql.SQLException;
/**
 * @author chenzy
 * @date 2020-07-15
 */
public class JDBCUtil {

    /**
     * 执行一个sql语句，增删改
     *
     * @param tableName
     * @param valueMap
     * @return
     */
    Boolean insertOne(String tableName, StringMap valueMap) {
        String sql = "";
        return executeOne(sql, valueMap.values().toArray());
    }

    Boolean executeOne(String sql, Object[] values) {
        PreparedStatement ps=null;
        try (var con = DataSourceHolder.getInstance().get().getConnection()) {
            ps = con.prepareStatement(sql);
            for (int i = 1; i <= values.length; i++) {
                ps.setObject(i, values[i]);
            }
            int count = ps.executeUpdate();
            //执行失败得到-1，返回false
            return -1 != count;
        } catch (SQLException e) {
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
