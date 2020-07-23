package com.czy.jdbc.sql;

import java.util.List;
import java.util.Optional;

/**
 * @author chenzy
 * @date 2020-07-22
 * 预处理sql语句，
 */
public class PreSql {
    private String sql;
    private List<Object> values;

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public void append(String sql) {
        this.sql += sql;
    }

    public PreSql append(Optional<PreSql> preSql) {
        preSql.ifPresent(sql -> {
            this.sql += sql.sql;
            this.values.addAll(sql.getValues());
        });
        return this;
    }

    public List<Object> getValues() {
        return values;
    }

    public void setValues(List<Object> values) {
        this.values = values;
    }

    public PreSql(String sql, List<Object> values) {
        this.sql = sql;
        this.values = values;
    }


}
