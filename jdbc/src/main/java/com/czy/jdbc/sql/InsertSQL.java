package com.czy.jdbc.sql;

import com.czy.util.model.StringMap;

import java.util.List;

/**
 * @author chenzy
 * @date 2020-07-21
 */
public class InsertSQL implements SQL{
    public InsertSQL(String preSql, List<Object> values) {
        this.preSql = preSql;
        this.values = values;
    }
    private String preSql;
    private List<Object> values;
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
        this.preSql = preSql;
    }
    @Override
    public List<Object> getValues() {
        return values;
    }
    public InsertSQL setColumnValues(StringMap columnMap) {
        if (columnMap == null || columnMap.isEmpty()) {
            return this;
        }
        var keys = columnMap.keySet().toString();
        preSql = preSql.replace("[columns]", keys.substring(1, keys.length() - 1));
        columnMap.values().forEach(value -> {
            values.add(value);
        });
        return this;
    }
}
