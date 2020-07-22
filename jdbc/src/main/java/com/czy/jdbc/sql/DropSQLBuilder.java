package com.czy.jdbc.sql;

import com.czy.jdbc.sql.enums.ReturnTypeEnum;

import java.util.List;

/**
 * @author chenzy
 * @date 2020-07-21
 */
public class DropSQLBuilder extends SQLBuilder {
    public DropSQLBuilder(PreSql preSql, ReturnTypeEnum returnType) {
        super(preSql, returnType);
    }
}
