package com.czy.jdbc.sql;


import com.czy.jdbc.exception.DaoException;
import com.czy.jdbc.sql.annotation.SQLAnnotation;
import com.czy.jdbc.sql.enums.ResultTypeEnum;
import com.czy.jdbc.sql.enums.SQLTypeEnum;
import com.czy.util.ClassUtil;
import com.czy.util.annotation.Par;
import com.czy.util.json.JsonUtil;
import com.czy.util.model.StringMap;
import com.czy.util.text.StringUtil;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;

/**
 * @author chenzy
 * @date 2020-07-17
 */
public class SQLFactory {
    private SQLFactory() {
    }

    public static SQLBuilder createSQL(SQLTypeEnum sqlTypeEnum, String sqlValue, ResultTypeEnum sqlReturnType, Object[] args) {
        var sql = StringUtil.isBlank(sqlValue) ? sqlTypeEnum.getMsg() : sqlValue;
        Class<SQLBuilder> sqlClass = sqlTypeEnum.getSqlClass();
        if (sqlClass == null) {
            return null;
        }
        SQLBuilder result = null;
        try {
            result = sqlClass.getDeclaredConstructor(String.class, List.class).newInstance(sql, new ArrayList<>());
            result.setResultType(sqlReturnType);
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

    public static <T extends SQLBuilder> T createSQL(Method method, Object[] args) throws DaoException {
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
                //sql结果集返回类型
                ResultTypeEnum resultSetType = (ResultTypeEnum) sqlReturnTypeMethod.invoke(annotation);
                //sql类型
                Class<T> sqlClass = sqlTypeEnum.getSqlClass();
                //创建sql对象
                T result = sqlClass.getDeclaredConstructor(String.class, List.class).newInstance(sqlValue, new ArrayList<>());
                result.setResultType(resultSetType);
                result.setReturnJavaType(method.getReturnType());
                var parameters = method.getParameters();
                //没有参数
                if (parameters == null || parameters.length == 0) {
                    return result;
                }
                if (parameters.length != args.length) {
                    throw new DaoException("方法参数长度错误！");
                }
                //sql拼接：把参数放到一个map中，
                var valueMap = new StringMap();
                for (var i = 0; i < parameters.length; i++) {
                    var par = parameters[i];
                    var parType = par.getType();
                    Par parAnnotation = parType.getAnnotation(Par.class);
                    //参数是基础数据类型且没有注解，则直接过滤
                    if (ClassUtil.isBasicDataType(parType) && parAnnotation == null) {
                        continue;
                    }
                    String key = parAnnotation == null ? parType.getSimpleName() : parAnnotation.value();
                    Object value = ClassUtil.isBasicDataType(parType) || Map.class.isAssignableFrom(parType) ?
                            args[i] : JsonUtil.model2Map(args[i]);
                    valueMap.add(key, value);
                }
                if (valueMap.isEmpty()) {
                    return result;
                }
                //对sql中的占位标记一一用?代替，并记住顺序
                int i = -1,j=-1;
                var orderMarking = new ArrayList<String>();
                while ((i = sqlValue.indexOf("#{"))!=-1 && (j=sqlValue.indexOf("}"))!=-1) {
                    orderMarking.add(sqlValue.substring(i,j));
                    sqlValue=sqlValue.substring(0,i)+"?"+sqlValue.substring(j);
                }
                //对这些顺序标记给上真正的值
                var values=new ArrayList<>();
                orderMarking.forEach(marking ->{
                    if (marking.contains(".")){
                        //说明是map嵌套map
                        var keys =marking.split(".");
                        for (int k = 0; k < keys.length; k++) {
                            valueMap.get(keys[k]);
                        }
                        MapUtil.getValue(keys);
                    }else {
                        values.add(valueMap.get(marking));
                    }
                });

                valueMap.forEach((key, value) -> {


                });
                Arrays.stream(args).forEach(arg -> {

                });
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
        sql = sql.replace("#{tableName}", tableName);
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
        whereMap.forEach((BiConsumer<String, Object>) (key, value) -> sql.where().equal(key, value));
        return sql;
    }

    public static UpdateSQLBuilder update(String tableName, StringMap columnMap) {
        var sql = createSQL(SQLTypeEnum.Delete, tableName, UpdateSQLBuilder.class);
        return sql.setColumnValues(columnMap);
    }

    public static UpdateSQLBuilder update(String tableName, StringMap columnMap, StringMap whereMap) {
        var sql = createSQL(SQLTypeEnum.Delete, tableName, UpdateSQLBuilder.class);
        sql.setColumnValues(columnMap);
        whereMap.forEach((BiConsumer<String, Object>) (key, value) -> sql.where().equal(key, value));
        return sql;
    }

    public static SelectSQLBuilder select(String tableName) {
        return createSQL(SQLTypeEnum.Select, tableName, SelectSQLBuilder.class);
    }

    public static SelectSQLBuilder select(String tableName, StringMap whereMap) {
        var sql = createSQL(SQLTypeEnum.Select, tableName, SelectSQLBuilder.class);
        whereMap.forEach((BiConsumer<String, Object>) (key, value) -> sql.where().equal(key, value));
        return sql;
    }

    /**
     * 清空表
     *
     * @param tableName
     * @return
     */
    public static String truncateTable(String tableName) {
        return createSQL(SQLTypeEnum.Truncate, tableName, TruncateSQLBuilder.class).getEndSql();
    }

    /**
     * 删除表
     *
     * @param tableName
     * @return
     */
    public static String dropTable(String tableName) {
        return createSQL(SQLTypeEnum.Truncate, tableName, DropSQLBuilder.class).getEndSql();
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
