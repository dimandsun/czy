package com.czy.jdbc.sql;

import com.czy.jdbc.sql.enums.ReturnTypeEnum;
import com.czy.util.model.StringMap;

import java.util.List;

/**
 * @author chenzy
 * @date 2020-07-21
 */
public class InsertSQLBuilder extends SQLBuilder {
    public InsertSQLBuilder(PreSql preSql, ReturnTypeEnum returnType) {
        super(preSql, returnType);
    }

    public InsertSQLBuilder setColumnValues(StringMap columnMap) {
        if (columnMap == null || columnMap.isEmpty()) {
            return this;
        }
        var preSql = getBasicPreSql();
        var keys = columnMap.keySet().toString();
        var sql = preSql.getSql().replace("[columns]", keys.substring(1, keys.length() - 1));
        preSql.setSql(sql);
        preSql.getValues().addAll(columnMap.values());
        return this;
    }
}
