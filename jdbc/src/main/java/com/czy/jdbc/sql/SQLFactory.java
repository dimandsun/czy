package com.czy.jdbc.sql;


import com.czy.jdbc.sql.annotation.SQLAnnotation;
import com.czy.jdbc.sql.enums.ReturnTypeEnum;
import com.czy.jdbc.sql.enums.SQLTypeEnum;
import com.czy.util.model.StringMap;
import com.czy.util.text.StringUtil;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.BiConsumer;

/**
 * @author chenzy
 * @date 2020-07-17
 */
public class SQLFactory {
    private SQLFactory() {
    }

    public static SQLBuilder createSQL(SQLTypeEnum sqlTypeEnum, String sqlValue, ReturnTypeEnum sqlReturnType, Object[] args) {
        var sql = StringUtil.isBlank(sqlValue) ? sqlTypeEnum.getMsg() : sqlValue;
        Class<SQLBuilder> sqlClass = sqlTypeEnum.getSqlClass();
        if (sqlClass == null) {
            return null;
        }
        SQLBuilder result = null;
        try {
            result = sqlClass.getDeclaredConstructor(String.class, List.class).newInstance(sql, new ArrayList<>());
            result.setReturnType(sqlReturnType);
            Arrays.stream(args).forEach(arg -> {
            });

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

    public static SQLBuilder createSQL(Method method, Object[] args) {
        try {
            for (var annotation : method.getAnnotations()) {
                Class annotationClass = annotation.annotationType();
                if (!annotationClass.isAnnotationPresent(SQLAnnotation.class)) {
                    continue;
                }
                var sqlAnnotation = (SQLAnnotation) annotationClass.getAnnotation(SQLAnnotation.class);
                //sql类型
                var sqlTypeEnum = sqlAnnotation.value();
                Method sqlValueMethod = null;
                sqlValueMethod = annotationClass.getMethod("value");
                var sqlReturnTypeMethod = annotationClass.getMethod("returnType");
                //sql语句
                String sqlValue = (String) sqlValueMethod.invoke(annotation);
                if (StringUtil.isBlank(sqlValue)) {
                    sqlValue = sqlTypeEnum.getMsg();
                }
                //sql返回类型
                ReturnTypeEnum sqlReturnType = (ReturnTypeEnum) sqlReturnTypeMethod.invoke(annotation);
                //sql类型
                Class<SQLBuilder> sqlClass = sqlTypeEnum.getSqlClass();
                //创建sql对象
                SQLBuilder result = sqlClass.getDeclaredConstructor(String.class, List.class).newInstance(sqlValue, new ArrayList<>());
                result.setReturnType(sqlReturnType);


                return result;
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static <T extends SQLBuilder> T createSQL(SQLTypeEnum sqlTypeEnum, String tableName, Class<T> sqlClass) {
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

    public static InsertSQLBuilder insert(String tableName, StringMap columnMap) {
        var sql = createSQL(SQLTypeEnum.Insert, tableName, InsertSQLBuilder.class);
        return sql.setColumnValues(columnMap);
    }

    public static DeleteSQLBuilder delete(String tableName) {
        return createSQL(SQLTypeEnum.Delete, tableName, DeleteSQLBuilder.class);
    }

    public static DeleteSQLBuilder delete(String tableName, StringMap whereMap) {
        var sql = createSQL(SQLTypeEnum.Delete, tableName, DeleteSQLBuilder.class);
        whereMap.forEach((BiConsumer<String, Object>) (key, value) -> sql.equal(key, value));
        return sql;
    }

    public static UpdateSQLBuilder update(String tableName, StringMap columnMap) {
        var sql = createSQL(SQLTypeEnum.Delete, tableName, UpdateSQLBuilder.class);
        return sql.setColumnValues(columnMap);
    }

    public static UpdateSQLBuilder update(String tableName, StringMap columnMap, StringMap whereMap) {
        var sql = createSQL(SQLTypeEnum.Delete, tableName, UpdateSQLBuilder.class);
        sql.setColumnValues(columnMap);
        whereMap.forEach((BiConsumer<String, Object>) (key, value) -> sql.equal(key, value));
        return sql;
    }

    public static SelectSQLBuilder select(String tableName) {
        return createSQL(SQLTypeEnum.Select, tableName, SelectSQLBuilder.class);
    }

    public static SelectSQLBuilder select(String tableName, StringMap whereMap) {
        var sql = createSQL(SQLTypeEnum.Select, tableName, SelectSQLBuilder.class);
        whereMap.forEach((BiConsumer<String, Object>) (key, value) -> sql.equal(key, value));
        return sql;
    }

    /**
     * 清空表
     *
     * @param tableName
     * @return
     */
    public static String truncateTable(String tableName) {
        return createSQL(SQLTypeEnum.Truncate, tableName, TruncateSQLBuilder.class).getEndPreSql();
    }

    /**
     * 删除表
     *
     * @param tableName
     * @return
     */
    public static String dropTable(String tableName) {
        return createSQL(SQLTypeEnum.Truncate, tableName, DropSQLBuilder.class).getEndPreSql();
    }

    /**
     * 创建表
     *
     * @return
     */
    public static String createTable(String sql) {
        return sql;
    }


}
