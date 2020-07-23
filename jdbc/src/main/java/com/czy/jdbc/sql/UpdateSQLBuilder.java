package com.czy.jdbc.sql;

import com.czy.jdbc.sql.enums.ResultTypeEnum;
import com.czy.util.model.StringMap;
import com.czy.util.text.StringUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;

/**
 * @author chenzy
 * @date 2020-07-21
 */
public class UpdateSQLBuilder extends SQLBuilder {
    private WhereSQL whereSQL;
    private PreSql setPreSql;
    public WhereSQL where() {
        return whereSQL==null?whereSQL=new WhereSQL(new PreSql(" where ",new ArrayList<>())):whereSQL;
    }
    public UpdateSQLBuilder(PreSql preSql, ResultTypeEnum returnType) {
        super(preSql, returnType);
    }
    public UpdateSQLBuilder setColumn(String key,Object value){
        if (setPreSql==null){
            setPreSql=new PreSql(" set "+key+"=?",List.of(value));
        }else {
            setPreSql.appendSql(","+key+"=?");
            setPreSql.getValues().add(value);
        }
        return this;
    }
    public UpdateSQLBuilder setColumnValues(StringMap columnMap) {
        if (columnMap == null || columnMap.isEmpty()) {
            return this;
        }
        columnMap.forEach((BiConsumer<String,Object>)(key, value)->setColumn(key,value));
        return this;
    }
    @Override
    public String getEndSql() {
        var sql=getBasicPreSql().getSql();
        if (StringUtil.isBlank(sql)){
            return "";
        }
        if (setPreSql==null){
            return "";
        }
        var setSql=setPreSql.getSql();
        if (StringUtil.isBlank(sql)){
            return "";
        }
        if (sql.contains("#{setContent}")){
            sql=sql.replace("#{setContent}",setSql);
        }else {
            sql += setSql;
        }
        if (whereSQL!=null){
            sql+=whereSQL.getEndSql();
        }
        return  sql;
    }

    @Override
    public List<Object> getEndValues() {
        var values = getBasicPreSql().getValues();
        if (setPreSql==null){
            return values;
        }
        values.addAll(setPreSql.getValues());
        if (whereSQL!=null){
            values.addAll(whereSQL.getEndValues());
        }
        return values;
    }
    public PreSql getSetPreSql() {
        return setPreSql;
    }

    public void setSetPreSql(PreSql setPreSql) {
        this.setPreSql = setPreSql;
    }
}
