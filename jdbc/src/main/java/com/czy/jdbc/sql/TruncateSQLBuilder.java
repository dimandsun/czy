package com.czy.jdbc.sql;

import com.czy.jdbc.sql.enums.ReturnTypeEnum;

import java.util.List;

/**
 * @author chenzy
 * @date 2020-07-21
 */
public class TruncateSQLBuilder implements SQLBuilder {
    private ReturnTypeEnum returnType;

    public TruncateSQLBuilder(String preSql) {
        this.preSql = preSql;
    }
    private String preSql;

    @Override
    public String getEndPreSql() {
        return preSql;
    }

    @Override
    public String getBasicPreSql() {
        return preSql;
    }

    @Override
    public void setPreSql(String preSql) {
        this.preSql=preSql;
    }
    @Override
    public void setReturnType(ReturnTypeEnum returnType) {
        this.returnType=returnType;
    }

    @Override
    public ReturnTypeEnum getReturnType() {
        return null;
    }

    @Override
    public List<Object> getValues() {
        return null;
    }
}
