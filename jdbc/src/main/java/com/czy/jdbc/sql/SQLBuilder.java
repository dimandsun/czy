package com.czy.jdbc.sql;

import com.czy.jdbc.sql.enums.ResultTypeEnum;

import java.lang.reflect.Type;
import java.util.List;

/**
 * @author chenzy
 * @date 2020-07-21
 * preSql 预处理sql，即用?代替参数值
 * values 参数值
 */
public class SQLBuilder<T> {
    private PreSql preSql;
    private ResultTypeEnum resultType;
    private Class returnJavaType;
    public SQLBuilder(PreSql preSql, ResultTypeEnum resultType) {
        setPreSql(preSql);
        setResultType(resultType);
    }

    public Class getReturnJavaType() {
        return returnJavaType;
    }

    public void setReturnJavaType(Class returnJavaType) {
        this.returnJavaType = returnJavaType;
    }
    public PreSql getEndSql(){
        return getBasicPreSql();
    }
    public PreSql getBasicPreSql(){
        return preSql;
    }
    public void setPreSql(PreSql preSql){
        this.preSql=preSql;
    }
    public void setResultType(ResultTypeEnum resultType){
        this.resultType = resultType;
    }

    public ResultTypeEnum getResultType() {
        return resultType;
    }
    public Object exec() {
        return null;
    }
}
