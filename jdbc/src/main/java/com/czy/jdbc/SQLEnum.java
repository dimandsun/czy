package com.czy.jdbc;

import com.czy.util.enums.IEnum;

/**
 * @author chenzy
 * @date 2020-07-17
 * insert,update,delete,select,drop,create,truncate
 * 数据操纵语言（DML）：用来操bai纵数据库中数据的命令.select、insert、update、delete。
 * 数据定义语言（DDL）：用来建立数据库、数据库对象和定义列的命令。包括：create、alter、drop。
 * 数据控制语言（DCL）：用来控制数据库组件的存取许可、权限等的命令。包括：grant、deny、revoke。
 */
public enum SQLEnum implements IEnum<String> {
    Insert("insert","insert into {tableName}([columns])values([values]);"),
    Delete("delete","delete from {tableName} where {column=value};"),
    Update("update","update {} set {} where {};"),
    Select("select","select {} from {} where {};"),
    Truncate("truncate","truncate table {};"),
    Create("create","create table {};"),
    Drop("drop","drop table {};"),
    Alter("alter","alter {};"),
    ,,,,
    ,;
    private String code;
    private String msg;

    SQLEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    @Override
    public String getValue() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
