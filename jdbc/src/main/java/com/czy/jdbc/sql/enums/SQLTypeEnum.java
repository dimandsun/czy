package com.czy.jdbc.sql.enums;

import com.czy.jdbc.sql.*;
import com.czy.util.enums.IEnum;

/**
 * @author chenzy
 * @date 2020-07-17
 * insert,update,delete,select,drop,create,truncate
 * 数据操纵语言（DML）：用来操bai纵数据库中数据的命令.select、insert、update、delete。
 * 数据定义语言（DDL）：用来建立数据库、数据库对象和定义列的命令。包括：create、alter、drop。
 * 数据控制语言（DCL）：用来控制数据库组件的存取许可、权限等的命令。包括：grant、deny、revoke。
 */
public enum SQLTypeEnum implements IEnum<String> {
    Insert("insert", InsertSQLBuilder.class,"insert into ${tableName}($[columns])values(#[values]);"),
    Delete("delete", DeleteSQLBuilder.class,"delete from ${tableName}"),
    Update("update", UpdateSQLBuilder.class,"update ${tableName} #{setContent}"),
    Select("select", SelectSQLBuilder.class,"select $[columns] from {tableName}"),
    Truncate("truncate", TruncateSQLBuilder.class,"truncate table ${tableName};"),
    Create("create",SQLBuilder.class,"${createContent}"),
    Drop("drop",SQLBuilder.class,"drop table ${tableName};"),
    Alter("alter",SQLBuilder.class,"alter ${};"),
    Liberty ("",SQLBuilder.class,""),
    ;
    private String sql;
    private String sqlTemplate;
    private  Class<SQLBuilder> sqlClass;

    SQLTypeEnum(String sql,Class sqlClass, String sqlTemplate) {
        this.sql = sql;
        this.sqlClass=sqlClass;
        this.sqlTemplate = sqlTemplate;
    }

    @Override
    public String getValue() {
        return sql;
    }

    public <T extends SQLBuilder>Class<T> getSqlClass() {
        return (Class<T>) sqlClass;
    }

    public String getSqlTemplate() {
        return sqlTemplate;
    }
}
