package com.czy.jdbc.sql;

import com.czy.jdbc.sql.enums.ReturnTypeEnum;
import com.czy.util.model.StringMap;
import com.czy.util.text.StringUtil;

import java.util.List;

/**
 * @author chenzy
 * @date 2020-07-21
 */
public class UpdateSQLBuilder extends WhereSQL implements SQLBuilder {
    private String preSql;
    private List<Object> values;
    private String setSql;
    private ReturnTypeEnum returnType;

    public UpdateSQLBuilder(String preSql, List<Object> values) {
        setSqlBuilder(this);
        this.preSql = preSql;
        this.values = values;
    }
    public UpdateSQLBuilder setColumnValues(StringMap columnMap) {
        if (columnMap == null || columnMap.isEmpty()) {
            return this;
        }
        if (setSql==null){
            setSql="";
        }
        columnMap.keySet().forEach(column->{
            setSql+=","+column+"=?";
        });
        if (setSql.startsWith(",")){
            setSql=setSql.substring(1);
        }
        columnMap.values().forEach(value -> {
            values.add(value);
        });
        return this;
    }
    @Override
    public String getEndPreSql() {
        if (StringUtil.isBlank(setSql)){
            return "";
        }
        preSql=preSql.replace("{setContent}",setSql);
        if (StringUtil.isBlank(getWhereSql())){
            return preSql;
        }
        return preSql+" where "+getWhereSql();
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
        return values;
    }
}
