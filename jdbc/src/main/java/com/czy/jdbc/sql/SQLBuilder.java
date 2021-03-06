package com.czy.jdbc.sql;

import com.czy.jdbc.pool.DataSourceHolder;
import com.czy.jdbc.exception.SQLParseException;
import com.czy.jdbc.sql.enums.ResultTypeEnum;
import com.czy.log.Log;
import com.czy.log.LogFactory;
import com.czy.util.text.StringUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Optional;

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
    public PreSql addSQLText(String sql){
        this.preSql.addSQLText(sql);
        return this.preSql;
    }
    public PreSql append(Optional<PreSql> preSql) {
        this.preSql.append(preSql);
        return this.preSql;
    }
    public PreSql addPars(Collection pars) {
        this.preSql.addPars(pars);
        return this.preSql;
    }
    public PreSql addPar(Object par) {
        this.preSql.addPar(par);
        return this.preSql;
    }

    public PreSql beforeExec() {
        var preSql = getPreSql();
        if (!preSql.isEnd()) {
            preSql.isEnd(true);
        }
        return preSql;
    }

    public PreSql getPreSql() {
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
        return con.prepareStatement(beforeExec().getSql());
    }

    /**
     * 默认获取受影响行
     * @param ps
     * @return
     * @throws SQLException
     */
    protected T getResult(PreparedStatement ps) throws SQLException {
        Object result= ps.executeUpdate();
        return (T) result;
    }
    public final T exec() throws SQLException {
        if (!preSql.isEnd()) {
            throw new SQLParseException("sql解析未结束");
        }
        var dataSource = DataSourceHolder.getInstance().get();
        var values = preSql.getPars();
        PreparedStatement ps = null;
        var sqlMsg = StringUtil.replaceALL(beforeExec().getSql(),"\\?",values);
        try (var con = dataSource.getConnection()) {
            ps = getPreparedStatement(con);
            if (ps == null) {
                throw new SQLException("sql语句错误，未明确的返回类型！");
            }
            for (int i = 1; i <= values.size(); i++) {
                ps.setObject(i, values.get(i-1));
            }
            log.debug(sqlMsg);
            return getResult(ps);
        } catch (SQLException e) {
            log.error(sqlMsg,e);
            throw e;
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
