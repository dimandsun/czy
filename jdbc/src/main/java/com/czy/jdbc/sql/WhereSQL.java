package com.czy.jdbc.sql;

import com.czy.jdbc.sql.enums.RelationEnum;
import com.czy.util.text.StringUtil;

/**
 * @author chenzy
 * @date 2020-07-21
 */
public abstract class WhereSQL {
    private SQL sql;
    private String whereSql;
    public void setSql(SQL sql) {
        this.sql = sql;
    }

    public String getWhereSql() {
        return whereSql;
    }

    public SQL and(){
        whereSql+="and ";
        return sql;
    }
    public SQL or(){
        whereSql+="or ";
        return sql;
    }
    /**
     * 小于 &lt;
     */
    public <T>SQL lt(String columnName,T value){
        return setWhereSql(RelationEnum.LT,columnName,value);
    }
    /**
     * 小于等于 	&le;
     */
    public <T>SQL le(String columnName,T value){
        return setWhereSql(RelationEnum.LE,columnName,value);
    }
    public <T>SQL equal(String columnName,T value){
        return setWhereSql(RelationEnum.Equal,columnName,value);
    }
    public <T>SQL notEqual(String columnName,T value){
        return setWhereSql(RelationEnum.NotEqual,columnName,value);
    }
    /**
     * 大于 &gt;
     */
    public <T>SQL gt(String columnName,T value){
        return setWhereSql(RelationEnum.GT,columnName,value);
    }
    /**
     * 大于等于 	&ge;
     */
    public <T>SQL ge(String columnName,T value){
        return setWhereSql(RelationEnum.GE,columnName,value);
    }
    public <T>SQL like(String columnName,T value){
        return setWhereSql(RelationEnum.Like,columnName,value);
    }
    public <T>SQL likeLeft(String columnName,T value){
        return setWhereSql(RelationEnum.LikeLeft,columnName,value);
    }
    public <T>SQL likeRight(String columnName,T value){
        return setWhereSql(RelationEnum.LikeRight,columnName,value);
    }


    private <T>SQL setWhereSql(RelationEnum relationEnum,String columnName,T value){
        if (StringUtil.isBlankOr(columnName,value)){
            return sql;
        }
        whereSql+=columnName+relationEnum.getValue();
        var temp=relationEnum.getValue().replace("{value}",value.toString());
        sql.getValues().add(temp);
        return sql;
    }
}
