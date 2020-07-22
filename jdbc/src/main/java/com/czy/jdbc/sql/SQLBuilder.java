package com.czy.jdbc.sql;

import com.czy.jdbc.sql.enums.ReturnTypeEnum;
import com.czy.util.model.Par;
import com.czy.util.text.StringUtil;

import java.util.List;

/**
 * @author chenzy
 * @date 2020-07-21
 * preSql 预处理sql，即用?代替参数值
 * values 参数值
 */
public class SQLBuilder {
    private PreSql preSql;
    private ReturnTypeEnum returnType;
    public SQLBuilder(PreSql preSql, ReturnTypeEnum returnType) {
        setPreSql(preSql);
        setReturnType(returnType);
    }
    public List<String> getEndValues() {
        return getBasicPreSql().getValues();
    }
    public String getEndSql() {
        return getBasicPreSql().getSql();
    }
    public PreSql getBasicPreSql(){
        return preSql;
    }
    public void setPreSql(PreSql preSql){
        this.preSql=preSql;
    }
    public void setReturnType(ReturnTypeEnum returnType){
        this.returnType=returnType;
    }

    public ReturnTypeEnum getReturnType() {
        return returnType;
    }
    public Object exec() {
        return null;
    }
}
