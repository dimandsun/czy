package com.czy.jdbc.sql;

import com.czy.jdbc.sql.enums.ReturnTypeEnum;
import com.czy.util.text.StringUtil;

import java.util.List;

/**
 * @author chenzy
 * @date 2020-07-21
 */
public class SelectSQLBuilder<T> extends WhereSQL implements SQLBuilder {
    private String orderBySql = null;
    private String preSql;
    private List<Object> values;
    private ReturnTypeEnum returnType;

    /*查询返回类型，注意当返回List<Bean>，returnClass为Bean,而不是List
        暂时只支持Bean和List<Bean>
    */
    private Class<T> returnClass;

    public SelectSQLBuilder(String preSql, List<Object> values) {
        setSqlBuilder(this);
        this.preSql = preSql;
        this.values = values;
    }
    /**
     * 升序 ASC
     */
    public SelectSQLBuilder<T> asc(String columnName) {
        return order(columnName, OrderBy.ASC);
    }

    /**
     * 降序 desc
     */
    public SelectSQLBuilder<T> desc(String columnName) {
        return order(columnName, OrderBy.Desc);
    }

    private SelectSQLBuilder<T> order(String columnName, String orderBy) {
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

    public Class<T> getReturnClass() {
        return returnClass;
    }

    public void setReturnClass(Class<T> returnClass) {
        this.returnClass = returnClass;
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
    public void setReturnType(ReturnTypeEnum returnType) {
        this.returnType=returnType;
    }

    @Override
    public ReturnTypeEnum getReturnType() {
        return returnType;
    }

    @Override
    public List<Object> getValues() {
        return values;
    }
}
