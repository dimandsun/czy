package com.czy.jdbc.sql;

import com.czy.javaLog.Log;
import com.czy.javaLog.LogFactory;
import com.czy.jdbc.pool.DataSourceHolder;
import com.czy.jdbc.exception.SQLParseException;
import com.czy.jdbc.sql.enums.ResultTypeEnum;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @author chenzy
 * @date 2020-07-21
 * preSql 预处理sql，即用?代替参数值
 * values 参数值
 */
public class SQLBuilder<T> {
    private static Log log = LogFactory.getLog("sql");
    private PreSql preSql;
    private ResultTypeEnum resultType;
    private Class<T> returnJavaType;

    public SQLBuilder(PreSql preSql, ResultTypeEnum resultType) {
        setPreSql(preSql);
        setResultType(resultType);
    }

    public Class<T> getReturnJavaType() {
        return returnJavaType;
    }

    public void setReturnJavaType(Class<T> returnJavaType) {
        this.returnJavaType = returnJavaType;
    }

    public PreSql getEndSql() {
        var preSql = getBasicPreSql();
        if (!preSql.isEnd()) {
            preSql.isEnd(true);
        }
        return preSql;
    }

    public PreSql getBasicPreSql() {
        return preSql;
    }

    public void setPreSql(PreSql preSql) {
        this.preSql = preSql;
    }

    public void setResultType(ResultTypeEnum resultType) {
        this.resultType = resultType;
    }

    public ResultTypeEnum getResultType() {
        return resultType;
    }

    /**
     * 默认获取预处理命令对象方式
     * @param con
     * @return
     * @throws SQLException
     */
    protected PreparedStatement getPreparedStatement(Connection con) throws SQLException {
        return con.prepareStatement(getEndSql().getSql());
    }

    /**
     * 默认获取受影响行
     * @param ps
     * @return
     * @throws SQLException
     */
    protected Object getResult(PreparedStatement ps) throws SQLException {
        return ps.executeUpdate();
    }
    public final Object exec() throws SQLParseException {
        if (!preSql.isEnd()) {
            throw new SQLParseException("sql解析未结束");
        }
        var dataSource = DataSourceHolder.getInstance().get();
        var values = preSql.getValues();
        PreparedStatement ps = null;
        String sqlMsg = null;
        try (var con = dataSource.getConnection()) {
            ps = getPreparedStatement(con);
            if (ps == null) {
                throw new SQLException("sql语句错误，未明确的返回类型！");
            }
            for (int i = 1; i <= values.size(); i++) {
                ps.setObject(i, values.get(i-1));
            }
            sqlMsg=ps.toString();
            log.debug(ps.toString());
            return getResult(ps);
        } catch (SQLException e) {
            log.error(sqlMsg==null?ps.toString():sqlMsg);
            e.printStackTrace();
            return null;
        } finally {
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }
}
