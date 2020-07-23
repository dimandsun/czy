package com.czy.jdbc;

import com.czy.jdbc.sql.SQLBuilder;
import com.czy.jdbc.sql.SQLFactory;
import com.czy.jdbc.sql.SelectSQLBuilder;
import com.czy.log.LogFactory;
import com.czy.util.json.JsonUtil;
import com.czy.util.model.StringMap;
import com.czy.log.Log;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static java.sql.Statement.RETURN_GENERATED_KEYS;

/**
 * @author chenzy
 * @date 2020-07-15
 */
public class JDBCUtil {
    private static Log log = LogFactory.getLog("sql");

    private JDBCUtil() {
    }

    /**
     * 新增一条记录
     *
     * @param tableName
     * @param columnMap
     * @return
     */
    public static Boolean insertOne(String tableName, StringMap columnMap) {
        var insertSQL = SQLFactory.insert(tableName, columnMap);
        var result = exec(DataSourceHolder.getInstance().get(), insertSQL);
        return true;
    }

    /**
     * 执行一个sql语句，修改
     */
    public static Boolean update(String tableName, StringMap setMap, StringMap whereMap) {
        var updateSQL = SQLFactory.update(tableName, setMap, whereMap);
        var result = exec(DataSourceHolder.getInstance().get(), updateSQL);
        return true;
    }

    /**
     * 执行一个sql语句，删除
     */
    public static Boolean delete(String tableName, StringMap whereMap) {
        var deleteSQL = SQLFactory.delete(tableName, whereMap);
        var result = exec(DataSourceHolder.getInstance().get(), deleteSQL);
        return true;
    }

    /**
     * 执行一个sql语句，查询
     */
    public static List<StringMap> select(String tableName, StringMap whereMap) {
        var selectSQL = SQLFactory.select(tableName, whereMap);
        return (List<StringMap>) exec(DataSourceHolder.getInstance().get(),selectSQL);
    }

    public static Object exec(DataSource dataSource, SQLBuilder sqlBuilder) {
        var values = sqlBuilder.getEndValues();
        PreparedStatement ps = null;
        try (var con = dataSource.getConnection()) {
            ps = switch (sqlBuilder.getResultType()) {
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
    }

}
