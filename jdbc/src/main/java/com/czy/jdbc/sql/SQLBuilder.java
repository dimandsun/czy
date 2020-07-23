package com.czy.jdbc.sql;

import com.czy.jdbc.DataSourceHolder;
import com.czy.jdbc.exception.SQLParseException;
import com.czy.jdbc.sql.enums.ResultTypeEnum;
import com.czy.util.json.JsonUtil;
import com.czy.util.model.StringMap;

import java.lang.reflect.Type;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static java.sql.Statement.RETURN_GENERATED_KEYS;

/**
 * @author chenzy
 * @date 2020-07-21
 * preSql 预处理sql，即用?代替参数值
 * values 参数值
 */
public class SQLBuilder<T> {
    private PreSql preSql;
    private ResultTypeEnum resultType;
    private Class returnJavaType;
    public SQLBuilder(PreSql preSql, ResultTypeEnum resultType) {
        setPreSql(preSql);
        setResultType(resultType);
    }

    public Class getReturnJavaType() {
        return returnJavaType;
    }

    public void setReturnJavaType(Class returnJavaType) {
        this.returnJavaType = returnJavaType;
    }
    public PreSql getEndSql(){
        var preSql=getBasicPreSql();
        if (!preSql.isEnd()){
            preSql.isEnd(true);
        }
        return preSql;
    }
    public PreSql getBasicPreSql(){
        return preSql;
    }
    public void setPreSql(PreSql preSql){
        this.preSql=preSql;
    }
    public void setResultType(ResultTypeEnum resultType){
        this.resultType = resultType;
    }

    public ResultTypeEnum getResultType() {
        return resultType;
    }
    public Object exec() throws SQLParseException {
        if (!preSql.isEnd()){
            throw new SQLParseException("sql解析未结束");
        }
        var dataSource=DataSourceHolder.getInstance().get();
        var values = preSql.getValues();
        PreparedStatement ps = null;
        try (var con = dataSource.getConnection()) {
            ps = switch (resultType) {
                case AffectedLines -> con.prepareStatement(sqlBuilder.getEndSql());
                case PrimaryKey -> con.prepareStatement(sqlBuilder.getEndSql(), RETURN_GENERATED_KEYS);
                case Cell, RecordOne, RecordList -> con.prepareCall(sqlBuilder.getEndSql());
                default -> null;
            };
            if (ps == null) {
                throw new SQLException("sql语句错误，未明确的返回类型！");
            }
            for (int i = 1; i <= values.size(); i++) {
                ps.setObject(i, values.get(i));
            }
            log.debug(ps.toString());
            Object result = switch (sqlBuilder.getResultType()) {
                case AffectedLines:
                    yield ps.executeUpdate();
                case PrimaryKey: {
                    ps.executeUpdate();
                    var resultSet = ps.getGeneratedKeys();
                    if (resultSet.next()) {
                        yield resultSet.getObject(1);
                    }
                    yield -1;
                }
                case Cell: {
                    var resultSet = ps.executeQuery();
                    if (resultSet.next()) {
                        yield resultSet.getObject(1);
                    }
                    yield null;
                }
                case RecordOne: {
                    if (sqlBuilder instanceof SelectSQLBuilder selectSQL) {
                        var resultSet = ps.executeQuery();
                        if (resultSet.next()) {
                            var metaData = resultSet.getMetaData();
                            var columnCount = metaData.getColumnCount();
                            var map = new StringMap(columnCount);
                            /*下标从1开始*/
                            for (int i = 1; i <= columnCount; i++) {
                                var columnName = metaData.getColumnName(i);
                                map.add(columnName, resultSet.getObject(i));
                            }
                            Class returnClass = selectSQL.getReturnJavaType();
                            if (returnClass == null) {
                                yield map;
                            }
                            yield JsonUtil.map2Model(map, returnClass);
                        }
                    } else {
                        throw new SQLException("sql类型错误：非查询sql不能返回单个记录");
                    }
                    yield null;
                }
                case RecordList: {
                    if (sqlBuilder instanceof SelectSQLBuilder selectSQL) {
                        var resultSet = ps.executeQuery();
                        var mapList = new ArrayList<StringMap>();
                        while (resultSet.next()) {
                            var metaData = resultSet.getMetaData();
                            var columnCount = metaData.getColumnCount();
                            var map = new StringMap(columnCount);
                            /*下标从1开始*/
                            for (int i = 1; i <= columnCount; i++) {
                                var columnName = metaData.getColumnName(i);
                                map.add(columnName, resultSet.getObject(i));
                            }
                            mapList.add(map);
                        }
                        Class returnClass = selectSQL.getReturnJavaType();
                        if (returnClass == null) {
                            yield mapList;
                        }
                        yield JsonUtil.model2Model(mapList, List.class, returnClass);
                    } else {
                        throw new SQLException("sql类型错误：非查询sql不能返回记录list");
                    }
                }
                default:
                    yield null;//不可能来这
            };
            return result;
        } catch (SQLException e) {
            log.error(ps.toString());
            e.printStackTrace();
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
            return null;
        }
        return null;
    }
}
