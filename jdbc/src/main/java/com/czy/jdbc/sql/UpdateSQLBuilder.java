package com.czy.jdbc.sql;

import com.czy.jdbc.sql.enums.ResultTypeEnum;
import com.czy.util.model.StringMap;
import com.czy.util.text.StringUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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
            setPreSql.append(","+key+"=?");
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
    public PreSql getEndSql() {
        var preSql=getBasicPreSql();
        if (!preSql.isEnd()){
            preSql.isEnd(true);
            if (setPreSql==null){
                return preSql;
            }
            preSql.replace("#{setContent}",Optional.of(setPreSql));
            if (whereSQL!=null){
                preSql.append(whereSQL.getEndSql());
            }
        }
        return preSql;
    }
    public void setSetPreSql(PreSql setPreSql) {
        this.setPreSql = setPreSql;
    }
}
