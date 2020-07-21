package com.czy.jdbc.sql;

import java.util.List;

/**
 * @author chenzy
 * @date 2020-07-21
 */
public class DropSQL implements SQL {
    public DropSQL(String preSql) {
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
    public List<Object> getValues() {
        return null;
    }
}
