package com.czy.jdbc.sql;


import com.czy.jdbc.sql.enums.SQLTypeEnum;
import com.czy.util.model.StringMap;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;

/**
 * @author chenzy
 * @date 2020-07-17
 */
public class SQLFactory {
    private SQLFactory() {
    }

    private static <T extends SQL> T createSQL(SQLTypeEnum sqlTypeEnum, String tableName, Class<T> sqlClass) {
        var sql = sqlTypeEnum.getMsg();
        sql = sql.replace("{tableName}", tableName);
        T result = null;
        try {
            result = sqlClass.getDeclaredConstructor(String.class, List.class).newInstance(sql, new ArrayList<>());
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static InsertSQL insert(String tableName, StringMap columnMap) {
        var sql = createSQL(SQLTypeEnum.Insert, tableName, InsertSQL.class);
        return sql.setColumnValues(columnMap);
    }
    public static DeleteSQL delete(String tableName) {
        return createSQL(SQLTypeEnum.Delete, tableName, DeleteSQL.class);
    }
    public static DeleteSQL delete(String tableName,StringMap whereMap) {
        var sql= createSQL(SQLTypeEnum.Delete, tableName, DeleteSQL.class);
        whereMap.forEach((BiConsumer<String,Object>)(key, value)-> sql.equal(key,value));
        return sql;
    }
    public static UpdateSQL update(String tableName, StringMap columnMap) {
        var sql = createSQL(SQLTypeEnum.Delete, tableName, UpdateSQL.class);
        return sql.setColumnValues(columnMap);
    }
    public static UpdateSQL update(String tableName, StringMap columnMap,StringMap whereMap) {
        var sql = createSQL(SQLTypeEnum.Delete, tableName, UpdateSQL.class);
        sql.setColumnValues(columnMap);
        whereMap.forEach((BiConsumer<String,Object>)(key, value)-> sql.equal(key,value));
        return sql;
    }
    public static SelectSQL select(String tableName) {
        return createSQL(SQLTypeEnum.Select, tableName, SelectSQL.class);
    }
    public static SelectSQL select(String tableName,StringMap whereMap) {
        var sql= createSQL(SQLTypeEnum.Select, tableName, SelectSQL.class);
        whereMap.forEach((BiConsumer<String,Object>)(key, value)-> sql.equal(key,value));
        return sql;
    }

    /**
     * 清空表
     * @param tableName
     * @return
     */
    public static String truncateTable(String tableName){
      return  createSQL(SQLTypeEnum.Truncate, tableName, TruncateSQL.class).getEndPreSql();
    }
    /**
     * 删除表
     * @param tableName
     * @return
     */
    public static String dropTable(String tableName){
        return  createSQL(SQLTypeEnum.Truncate, tableName, DropSQL.class).getEndPreSql();
    }
    /**
     * 创建表
     * @return
     */
    public static String createTable(String sql){
        return sql;
    }
}
