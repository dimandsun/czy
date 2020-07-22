package com.czy.jdbc.sql;

import java.util.List;

/**
 * @author chenzy
 * @date 2020-07-22
 * 预处理sql语句，
 */
public class PreSql {
    private String sql;
    private List<String> values;

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public List<String> getValues() {
        return values;
    }

    public void setValues(List<String> values) {
        this.values = values;
    }

    public PreSql(String sql, List<String> values) {
        this.sql = sql;
        this.values = values;
    }

    public void appendSql(String str) {
        sql+=str;
    }
}
