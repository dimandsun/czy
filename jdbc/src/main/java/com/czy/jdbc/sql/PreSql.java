package com.czy.jdbc.sql;

import com.czy.util.text.StringUtil;

import java.util.Collection;
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
    /*sql拼接到末尾了*/
    private Boolean isEnd=false;

    public Boolean isEnd() {
        return isEnd;
    }
    public void isEnd(Boolean isEnd) {
        this.isEnd = isEnd;
    }

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
    public PreSql replace(String marking, Optional<PreSql> preSql) {
        preSql.ifPresent(sql -> {
            this.sql = this.sql.replace(marking,sql.getSql());
            this.values.addAll(sql.getValues());
        });
        return this;
    }
    public List<Object> getValues() {
        return List.copyOf(values);
    }

    public void setValues(List<Object> values) {
        this.values = values;
    }


    public PreSql(String sql, List<Object> values) {
        this.sql = sql;
        this.values = values;
    }

    public PreSql addAll(Collection values) {
        if (values==null||values.isEmpty()){
            return this;
        }
        this.values.addAll(values);
        return this;
    }
    public PreSql add(Object value) {
        if (StringUtil.isBlank(value)){
            return this;
        }
        values.add(value);
        return this;
    }
}
