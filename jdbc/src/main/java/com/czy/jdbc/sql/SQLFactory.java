package com.czy.jdbc.sql;


import com.czy.jdbc.exception.DaoException;
import com.czy.jdbc.sql.annotation.SQLAnnotation;
import com.czy.jdbc.sql.enums.ResultTypeEnum;
import com.czy.jdbc.sql.enums.SQLTypeEnum;
import com.czy.util.ClassUtil;
import com.czy.util.annotation.Par;
import com.czy.util.json.JsonUtil;
import com.czy.util.model.StringMap;
import com.czy.util.list.MapUtil;
import com.czy.util.text.StringUtil;

import java.lang.reflect.*;
import java.util.ArrayList;
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

    private static <T extends SQLBuilder> T createSQL(SQLTypeEnum sqlTypeEnum, String tableName, Class<T> sqlClass) {
        var sql = sqlTypeEnum.getMsg();
        sql = sql.replace("${tableName}", tableName);
        T result = null;
        try {
            var resultSetType=switch (sqlTypeEnum){
                case Insert -> ResultTypeEnum.PrimaryKey;
                case Select -> ResultTypeEnum.RecordList;
                case Delete, Update, Truncate, Create, Drop, Alter, Other -> ResultTypeEnum.AffectedLines;
            };
            result = sqlClass.getDeclaredConstructor(PreSql.class, ResultTypeEnum.class).newInstance(new PreSql(sql, new ArrayList<>()), resultSetType);
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
                //创建sql对象 PreSql preSql, ResultTypeEnum returnType
                T sqlBuilder = sqlClass.getDeclaredConstructor(PreSql.class, ResultTypeEnum.class).newInstance(new PreSql(sqlValue, new ArrayList<>()), resultSetType);
                sqlBuilder.setResultType(resultSetType);
                sqlBuilder.setReturnJavaType(method.getReturnType());
                /*解析方法返回类型：暂时只支持List<Bean>形式泛型*/
                Type type=method.getGenericReturnType();
                if(type instanceof ParameterizedType){
                    ParameterizedType type1= (ParameterizedType) type;
                    if(type1.getRawType()==List.class){
                        Type[] typeArguments = type1.getActualTypeArguments();
                        if (typeArguments!=null&&typeArguments.length>0){
                            sqlBuilder.setReturnJavaType((Class) typeArguments[0]);
                        }
                    }
                }
                if (sqlBuilder.getReturnJavaType()==null){
                    sqlBuilder.setReturnJavaType(method.getReturnType());
                }
                /*解析方法参数*/
                Parameter[] parameters = method.getParameters();
                if (parameters == null || parameters.length == 0) {
                    return sqlBuilder;
                }
                if (parameters.length != args.length) {
                    throw new DaoException("方法参数长度错误！");
                }
                /*sql拼接：把参数以键值对形式放到一个valueMap中，
                    key为参数名或者参数的par注解值，
                    value:当实际值不是基本数据类型,转成map*/
                var valueMap = new StringMap();
                String setParKey = null;//表对应的实体对象在valueMap中的key
                String whereParKey = null;//表对应的实体对象在valueMap中的key
                for (var i = 0; i < parameters.length; i++) {
                    var par = parameters[i];
                    var parType = par.getType();
                    Par parAnnotation = par.getAnnotation(Par.class);
                    //参数是基础数据类型且没有注解，则直接过滤
                    if (ClassUtil.isBasicDataType(parType) && parAnnotation == null) {
                        continue;
                    }
                    String key = parAnnotation == null ? par.getName() : parAnnotation.value();
                    Object value = ClassUtil.isBasicDataType(parType) || Map.class.isAssignableFrom(parType) ?
                            args[i] : JsonUtil.model2Map(args[i]);
                    valueMap.add(key, value);
                    if (!ClassUtil.isBasicDataType(parType) && !Map.class.isAssignableFrom(parType)) {
                        if (key.contains("set")) {
                            setParKey = key;
                        } else if (key.contains("where")) {
                            whereParKey = key;
                        }
                    }
                }
                if (valueMap.isEmpty()) {
                    return sqlBuilder;
                }
                /*对sql中的占位标记${}用实际值代替*/
                {
                    int i = -1, j = -1;
                    while ((i = sqlValue.indexOf("${")) != -1 && (j = sqlValue.indexOf("}")) != -1) {
                        var temp=sqlValue.substring(i + 2, j);
                        var value = MapUtil.getValue(valueMap,temp.contains(".")?temp.split("\\."):temp).get();
                        sqlValue = sqlValue.substring(0, i) + value + sqlValue.substring(j + 1);
                    }
                }
                sqlBuilder.getBasicPreSql().setSql(sqlValue);
                /*
                对sql中的占位标记#[]用#{}代替
                    $[]用${}代替
                这些占位标记表示接受的是setBean或者whereBean
                */
                if (setParKey != null && sqlBuilder instanceof SetColumnValues setColumnValues) {
                    setColumnValues.setColumnValues((Map) valueMap.get(setParKey));
                }
                if (whereParKey != null && sqlBuilder instanceof WhereColumnValues whereColumnValues) {
                    whereColumnValues.whereColumnValues((Map) valueMap.get(setParKey));
                }

                /*对sql中的占位标记#{}一一用?代替，并记住顺序
                 注意：当存在setParKey或者whereParKey，且还有#{}标记，sql拼接很有可能有问题，
                */
                var orderMarking = new ArrayList<String>();
                {
                    sqlValue=sqlBuilder.beforeExec().getSql();
                    int i = -1, j = -1;
                    while ((i = sqlValue.indexOf("#{")) != -1 && (j = sqlValue.indexOf("}")) != -1) {
                        orderMarking.add(sqlValue.substring(i + 2, j));
                        sqlValue = sqlValue.substring(0, i) + "?" + sqlValue.substring(j + 1);
                    }
                }
                /*对这些顺序标记给上真正的值*/
                orderMarking.forEach(marking -> sqlBuilder.beforeExec().add(MapUtil.getValue(valueMap, marking.split(".")).get()));
                sqlBuilder.beforeExec().setSql(sqlValue);
                sqlBuilder.beforeExec().isEnd(true);
                return sqlBuilder;
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

    public static InsertSQLBuilder insert(String tableName, StringMap columnMap) {
        var sql = createSQL(SQLTypeEnum.Insert, tableName, InsertSQLBuilder.class);
        sql.setColumnValues(columnMap);
        return sql;
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
    public static <T>SelectSQLBuilder<T> select(String tableName,Class<T> returnType) {
        var sqlBuilder= createSQL(SQLTypeEnum.Select, tableName, SelectSQLBuilder.class);
        sqlBuilder.setReturnJavaType(returnType);
        return sqlBuilder;
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
        return createSQL(SQLTypeEnum.Truncate, tableName, TruncateSQLBuilder.class).beforeExec().getSql();
    }

    /**
     * 删除表
     *
     * @param tableName
     * @return
     */
    public static String dropTable(String tableName) {
        return createSQL(SQLTypeEnum.Truncate, tableName, DropSQLBuilder.class).beforeExec().getSql();
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
