package com.czy.jdbc.sql;

import com.czy.jdbc.sql.enums.ResultTypeEnum;
import com.czy.util.json.JsonUtil;
import com.czy.util.model.StringMap;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;


/**
 * @author chenzy
 * @date 2020-07-21
 */
public class InsertSQLBuilder<T> extends SQLBuilder<T> {
    public InsertSQLBuilder(PreSql preSql, ResultTypeEnum returnType) {
        super(preSql, returnType);
    }

    public InsertSQLBuilder setColumnValues(StringMap columnMap) {
        if (columnMap == null || columnMap.isEmpty()) {
            return this;
        }
        var preSql = getBasicPreSql();
        var keys = columnMap.keySet().toString();
        var sql = preSql.getSql().replace("#[columns]", keys.substring(1, keys.length() - 1));
        preSql.setSql(sql);
        preSql.getValues().addAll(columnMap.values());
        return this;
    }

    protected PreparedStatement getPreparedStatement(Connection con) throws SQLException {
        return switch (getResultType()) {
            case PrimaryKey -> con.prepareStatement(getEndSql().getSql(), Statement.RETURN_GENERATED_KEYS);
            default -> con.prepareStatement(getEndSql().getSql());
        };
    }

    @Override
    protected Object getResult(PreparedStatement ps) throws SQLException {
        return switch (getResultType()) {
            case AffectedLines: yield ps.executeUpdate();
            case PrimaryKey: {
                ps.executeUpdate();
                var resultSet = ps.getGeneratedKeys();
                if (resultSet.next()) {
                    yield resultSet.getObject(1);
                }
                yield -1;
            }
            default: throw new SQLException("sql类型异常！");
        };
    }
}
