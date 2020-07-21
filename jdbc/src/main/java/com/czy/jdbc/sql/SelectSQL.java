package com.czy.jdbc.sql;

import com.czy.util.text.StringUtil;

import java.util.List;

/**
 * @author chenzy
 * @date 2020-07-21
 */
public class SelectSQL extends WhereSQL implements SQL {
    private String orderBySql = null;
    private String preSql;
    private List<Object> values;

    public SelectSQL(String preSql, List<Object> values) {
        setSql(this);
        this.preSql = preSql;
        this.values = values;
    }
    /**
     * 升序 ASC
     */
    public <T> SelectSQL asc(String columnName) {
        return order(columnName, OrderBy.ASC);
    }

    /**
     * 降序 desc
     */
    public <T> SelectSQL desc(String columnName) {
        return order(columnName, OrderBy.Desc);
    }

    private <T> SelectSQL order(String columnName, String orderBy) {
        if (StringUtil.isBlank(columnName)) {
            return this;
        }
        if (orderBySql == null) {
            orderBySql = "order by " + columnName + " " + orderBy;
            return this;
        }
        orderBySql += "," + columnName + " " + orderBy;
        return this;
    }

    private interface OrderBy {
        String ASC = "asc";
        String Desc = "desc";
    }
    @Override
    public String getEndPreSql() {
        if (StringUtil.isBlank(getWhereSql())){
            return preSql;
        }
        var sql = preSql+" where "+getWhereSql();
        return orderBySql==null ? sql : sql+orderBySql;
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
}
