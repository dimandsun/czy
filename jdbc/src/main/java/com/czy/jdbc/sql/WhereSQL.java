package com.czy.jdbc.sql;

import com.czy.jdbc.sql.enums.RelationEnum;
import com.czy.util.text.StringUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author chenzy
 * @date 2020-07-21
 */
public class WhereSQL {
    private PreSql preSql;
    public WhereSQL(PreSql preSql) {
        this.preSql = preSql;
    }
    public String getEndSql() {
        if (preSql==null){
            return "";
        }
        if (preSql.getSql().endsWith("where ")){
            return "";
        }
        return preSql.getSql();
    }
    public List<String> getEndValues() {
        if (preSql==null){
            return List.of();
        }
        return preSql.getValues();
    }
    public WhereSQL and(){
        preSql.appendSql("and ");
        return this;
    }
    public WhereSQL or(){
        preSql.appendSql("or ");
        return this;
    }
    /**
     * 小于 &lt;
     */
    public <T> WhereSQL lt(String columnName, T value){
        return setWhereSql(RelationEnum.LT,columnName,value);
    }
    /**
     * 小于等于 	&le;
     */
    public <T> WhereSQL le(String columnName, T value){
        return setWhereSql(RelationEnum.LE,columnName,value);
    }
    public <T> WhereSQL equal(String columnName, T value){
        return setWhereSql(RelationEnum.Equal,columnName,value);
    }
    public <T> WhereSQL notEqual(String columnName, T value){
        return setWhereSql(RelationEnum.NotEqual,columnName,value);
    }
    /**
     * 大于 &gt;
     */
    public <T> WhereSQL gt(String columnName, T value){
        return setWhereSql(RelationEnum.GT,columnName,value);
    }
    /**
     * 大于等于 	&ge;
     */
    public <T> WhereSQL ge(String columnName, T value){
        return setWhereSql(RelationEnum.GE,columnName,value);
    }
    public <T> WhereSQL like(String columnName, T value){
        return setWhereSql(RelationEnum.Like,columnName,value);
    }
    public <T> WhereSQL likeLeft(String columnName, T value){
        return setWhereSql(RelationEnum.LikeLeft,columnName,value);
    }
    public <T> WhereSQL likeRight(String columnName, T value){
        return setWhereSql(RelationEnum.LikeRight,columnName,value);
    }


    private <T> WhereSQL setWhereSql(RelationEnum relationEnum, String columnName, T value){
        if (StringUtil.isBlankOr(columnName,value)){
            return this;
        }
        preSql.appendSql(columnName+relationEnum.getValue());
        var temp=relationEnum.getValue().replace("{value}",value.toString());
        preSql.getValues().add(temp);
        return this;
    }
}
