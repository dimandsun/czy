package com.czy.jdbc.sql;

import com.czy.jdbc.sql.enums.ResultTypeEnum;

/**
 * @author chenzy
 * @date 2020-07-21
 */
public class DropSQLBuilder extends SQLBuilder {
    public DropSQLBuilder(PreSql preSql, ResultTypeEnum returnType) {
        super(preSql, returnType);
    }
}
