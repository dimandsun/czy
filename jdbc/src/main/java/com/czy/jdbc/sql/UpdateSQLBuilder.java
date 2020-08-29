package com.czy.jdbc.sql;

import com.czy.jdbc.sql.enums.ResultTypeEnum;
import com.czy.util.model.StringMap;
import com.czy.util.text.StringUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiConsumer;

/**
 * @author chenzy
 * @date 2020-07-21
 */
public class UpdateSQLBuilder extends SQLBuilder<Integer> implements SetColumnValues, WhereColumnValues {
    private WhereSQL whereSQL;
    private PreSql setPreSql;

    public WhereSQL where(WhereSQL whereSQL){
        this.whereSQL=whereSQL;
        return this.whereSQL;
    }

    public WhereSQL where() {
        return whereSQL == null ? where(WhereSQL.newInstance()) : whereSQL;
    }

    public UpdateSQLBuilder(PreSql preSql, ResultTypeEnum returnType) {
        super(preSql, returnType);
    }

    public UpdateSQLBuilder setColumn(String key, Object value) {
        if (setPreSql == null) {
            setPreSql = new PreSql(" set " + key + "=?", List.of(value));
        } else {
            setPreSql.addSQLText("," + key + "=?");
            setPreSql.addPar(value);
        }
        return this;
    }

    @Override
    public UpdateSQLBuilder setColumnValues(Map columnMap) {
        if (columnMap == null || columnMap.isEmpty()) {
            return this;
        }
        columnMap.forEach((BiConsumer<String, Object>) (key, value) -> setColumn(key, value));
        return this;
    }

    @Override
    public WhereSQL whereColumnValues(Map columnMap) {
        return where().equal(columnMap);
    }

    @Override
    public PreSql beforeExec() {
        var preSql = getPreSql();
        if (!preSql.isEnd()) {
            preSql.isEnd(true);
            if (setPreSql == null) {
                return preSql;
            }
            preSql.replace("#{setContent}", Optional.of(setPreSql));
            if (whereSQL != null) {
                preSql.append(whereSQL.getEndSql());
            }
        }
        return preSql;
    }
    public UpdateSQLBuilder addSetSQL(String setSQLText) {
        if (setPreSql == null) {
            setPreSql = new PreSql(" set " +setSQLText,new ArrayList<>());
        } else {
            setPreSql.addSQLText("," + setSQLText);
        }
        return this;
    }
    public void setSetPreSql(PreSql setPreSql) {
        this.setPreSql = setPreSql;
    }
}
