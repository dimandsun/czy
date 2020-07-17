package com.czy.jdbc;


import com.czy.util.model.StringMap;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.BiConsumer;

/**
 * @author chenzy
 * @date 2020-07-17
 */
public class SQLBuilder {
    //预处理sql，即用?代替参数值
    private StringBuffer preSql = new StringBuffer();
    private List<String> columns;
    private List<Object> values;

    private SQLBuilder begin(SQLEnum sqlEnum, String tableName) {
        String sql = switch (sqlEnum) {
            case Insert -> "insert into " + tableName;
            case Delete -> "delete from " + tableName;
            case Update -> "update " + tableName;
            case Select -> "select " + tableName;
            case Truncate -> "truncate table " + tableName + ";";
            case Create -> "create table " + tableName;
            case Drop -> "drop table " + tableName;
            case Alter -> "alter ";
            default -> "";
        };
        sql=sqlEnum.getMsg();
        sql=sql.replace("{tableName}",tableName);
        preSql.append(sql);
        return this;
    }
    public SQLBuilder insert(String tableName) {
        begin(SQLEnum.Insert, tableName);
        return this;
    }
    public SQLBuilder column(StringMap columnMap) {
        if (columnMap==null||columnMap.isEmpty()){
            return this;
        }
        if (columns==null){
            columns=new ArrayList<>(columnMap.size());
        }
        if (values==null){
            values = new ArrayList<>(columnMap.size());
        }
        columnMap.forEach((BiConsumer<String, Object>) (key,value)->{
            columns.add(key);
            values.add(value);
        });
        return this;
    }
    public String preSql() {

//        insert into {tableName}([columns])values([values]);

        return preSql.toString();
    }
    public List preValues() {
        return null;
    }
}
