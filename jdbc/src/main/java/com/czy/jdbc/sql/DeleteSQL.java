package com.czy.jdbc.sql;

import com.czy.util.text.StringUtil;

import java.util.List;

/**
 * @author chenzy
 * @date 2020-07-21
 */
public class DeleteSQL extends WhereSQL implements SQL {

    public DeleteSQL(String preSql, List<Object> values) {
        setSql(this);
        this.preSql = preSql;
        this.values = values;
    }
    private String preSql;
    private List<Object> values;

    @Override
    public String getEndPreSql() {
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
        this.preSql = preSql;
    }

    @Override
    public List<Object> getValues() {
        return values;
    }

}
