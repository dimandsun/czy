package com.czy.jdbc;


import com.czy.util.model.StringMap;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.BiConsumer;

/**
 * @author chenzy
 * @date 2020-07-17
 */
public class SQLBuilder {
    private SQLBuilder(){}
    //预处理sql，即用?代替参数值
    private String preSql = null;
    private List<Object> values=null;
    private WhereSQL whereSQL=null;
    private static SQLBuilder create(SQLEnum sqlEnum, String tableName) {
        var sqlBuilder=new SQLBuilder();
        var sql = sqlEnum.getMsg();
        sql = sql.replace("{tableName}", tableName);
        sqlBuilder.preSql = sql;
        return sqlBuilder;
    }
    public static SQLBuilder insert(String tableName,StringMap columnMap){
        var sqlBuilder= create(SQLEnum.Insert, tableName);
        if (columnMap == null || columnMap.isEmpty()) {
            return sqlBuilder;
        }
        var keys = columnMap.keySet().toString();
        sqlBuilder.preSql=sqlBuilder.preSql.replace("[columns]",keys.substring(1,keys.length()-1));
        if (sqlBuilder.values==null){
            sqlBuilder.values=new ArrayList<>();
        }
        columnMap.values().forEach(value->{
            sqlBuilder.values.add(value);
        });
        return sqlBuilder;
    }
    public static WhereSQL delete(String tableName){
        return create(SQLEnum.Insert, tableName).getWhereSQL();
    }
    private WhereSQL getWhereSQL(){
        if (whereSQL==null){

        }
        return whereSQL = new WhereSQL();
    }
    class WhereSQL{
        private String whereSql;
        private WhereSQL(){
            whereSql="where ";
        }
        public WhereSQL and(){
            whereSql+="and ";
            return this;
        }
        public WhereSQL or(){
            whereSql+="or ";
            return this;
        }
        public <T>WhereSQL equal(String columnName,T value){
            whereSql+=columnName+"=? ";
            SQLBuilder.this.values.add(value);
            return this;
        }
        public <T>WhereSQL notEqual(String columnName,T value){
            whereSql+=columnName+"!=? ";
            SQLBuilder.this.values.add(value);
            return this;
        }
    }
}
