package com.czy.jdbc.sql;

import com.czy.jdbc.sql.enums.ResultTypeEnum;
import com.czy.util.text.StringUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;


/**
 * @author chenzy
 * @date 2020-07-21
 */
public class InsertSQLBuilder<T> extends SQLBuilder<T> implements SetColumnValues {
    public InsertSQLBuilder(PreSql preSql, ResultTypeEnum returnType) {
        super(preSql, returnType);
    }
    @Override
    public InsertSQLBuilder setColumnValues(Map columnMap) {
        if (columnMap == null || columnMap.isEmpty()) {
            return this;
        }
        var preSql = getPreSql();
        var keys = columnMap.keySet().toString();
        var sql = preSql.getSql().replace("$[columns]", keys.substring(1, keys.length() - 1))
                .replace("#[values]", StringUtil.copy("?",columnMap.size(),","));
        preSql.setSql(sql);
        preSql.addPars(columnMap.values());
        return this;
    }

    protected PreparedStatement getPreparedStatement(Connection con) throws SQLException {
        return switch (getResultType()) {
            case PrimaryKey -> con.prepareStatement(beforeExec().getSql(), Statement.RETURN_GENERATED_KEYS);
            default -> con.prepareStatement(beforeExec().getSql());
        };
    }

    @Override
    protected T getResult(PreparedStatement ps) throws SQLException {
        var returnJavaType=getReturnJavaType();
        return switch (getResultType()) {
            case AffectedLines: yield super.getResult(ps);
            case PrimaryKey: {
                ps.executeUpdate();
                var resultSet = ps.getGeneratedKeys();
                if (resultSet.next()) {
                    var temp =resultSet.getObject(1);
                    if (returnJavaType==Integer.class){
                        yield (T)StringUtil.getInt(temp);
                    }
                    yield (T)temp;
                }
                if (returnJavaType==Integer.TYPE||returnJavaType==Long.TYPE){
                    Object result=-1;
                    yield (T)result;
                }else {
                    Object result="-1";
                    yield (T)result;
                }
            }
            default: throw new SQLException("sql类型异常！");
        };
    }
}
