package com.czy.jdbc.sql;

import com.czy.jdbc.sql.enums.ReturnTypeEnum;
import com.czy.util.text.StringUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author chenzy
 * @date 2020-07-21
 */
public class DeleteSQLBuilder extends SQLBuilder {
    private WhereSQL whereSQL;

    public DeleteSQLBuilder(PreSql preSql, ReturnTypeEnum returnType) {
        super(preSql, returnType);
    }
    public WhereSQL where() {
        return whereSQL==null?whereSQL=new WhereSQL(new PreSql(" where ",new ArrayList<>())):whereSQL;
    }
    @Override
    public String getEndSql() {
        var sql=getBasicPreSql().getSql();
        if (whereSQL!=null){
            sql+=whereSQL.getEndSql();
        }
        return sql;
    }
    @Override
    public List<String> getEndValues() {
        var values=getBasicPreSql().getValues();
        if (whereSQL!=null){
            values.addAll(whereSQL.getEndValues());
        }
        return values;
    }
}
