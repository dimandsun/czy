package com.czy.jdbc.sql;

import com.czy.jdbc.sql.enums.ResultTypeEnum;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author chenzy
 * @date 2020-07-21
 */
public class DeleteSQLBuilder extends SQLBuilder {
    private WhereSQL whereSQL;

    public DeleteSQLBuilder(PreSql preSql, ResultTypeEnum returnType) {
        super(preSql, returnType);
    }

    public WhereSQL where() {
        return whereSQL == null ? whereSQL = WhereSQL.newInstance() : whereSQL;
    }


    @Override
    public PreSql beforeExec() {
        var preSql=getPreSql();
        if (!preSql.isEnd()){
            if (whereSQL != null) {
                preSql.append(whereSQL.getEndSql());
            }
            preSql.isEnd(true);
        }

        return preSql;
    }
}
