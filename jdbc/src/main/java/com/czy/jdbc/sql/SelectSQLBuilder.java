package com.czy.jdbc.sql;

import com.czy.jdbc.sql.enums.ResultTypeEnum;
import com.czy.util.text.StringUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author chenzy
 * @date 2020-07-21
 */
public class SelectSQLBuilder extends SQLBuilder {


    private WhereSQL whereSQL;
    private PreSql orderPreSql;
    public SelectSQLBuilder(PreSql preSql, ResultTypeEnum returnType) {
        super(preSql, returnType);
    }
    public WhereSQL where() {
        return whereSQL==null?whereSQL=new WhereSQL(new PreSql(" where ",new ArrayList<>())):whereSQL;
    }
    /**
     * 升序 ASC
     */
    public SelectSQLBuilder asc(String columnName) {
        return order(columnName, OrderBy.ASC);
    }

    /**
     * 降序 desc
     */
    public SelectSQLBuilder desc(String columnName) {
        return order(columnName, OrderBy.Desc);
    }

    private SelectSQLBuilder order(String columnName, String orderBy) {
        if (StringUtil.isBlank(columnName)) {
            return this;
        }
        if (orderPreSql == null) {
            orderPreSql = new PreSql("order by ? ?",new ArrayList<>());
        }else {
            orderPreSql.append(",? ?");
        }
        orderPreSql.getValues().add(columnName);
        orderPreSql.getValues().add(orderBy);
        return this;
    }

    private interface OrderBy {
        String ASC = "asc";
        String Desc = "desc";
    }
    @Override
    public PreSql getEndSql() {
        var preSql=getBasicPreSql();
        if (whereSQL!=null){
            preSql.append(whereSQL.getEndSql());
        }
        preSql.append(Optional.ofNullable(orderPreSql));
        return preSql;
    }
}
