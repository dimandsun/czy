package com.czy.jdbc.sql;

import com.czy.jdbc.sql.enums.ResultTypeEnum;
import com.czy.util.json.JsonUtil;
import com.czy.util.model.StringMap;
import com.czy.util.text.StringUtil;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @author chenzy
 * @date 2020-07-21
 */
public class SelectSQLBuilder<T> extends SQLBuilder<T> {


    private WhereSQL whereSQL;
    private PreSql orderPreSql;

    public SelectSQLBuilder(PreSql preSql, ResultTypeEnum returnType) {
        super(preSql, returnType);
    }

    public WhereSQL where() {
        return whereSQL == null ? whereSQL = new WhereSQL(new PreSql(" where ", new ArrayList<>())) : whereSQL;
    }

    /**
     * 升序 ASC
     */
    public SelectSQLBuilder asc(String columnName) {
        return order(columnName, OrderBy.ASC);
    }

    /**
     * 降序 desc
     */
    public SelectSQLBuilder desc(String columnName) {
        return order(columnName, OrderBy.Desc);
    }

    private SelectSQLBuilder order(String columnName, String orderBy) {
        if (StringUtil.isBlank(columnName)) {
            return this;
        }
        if (orderPreSql == null) {
            orderPreSql = new PreSql("order by ? ?", new ArrayList<>());
        } else {
            orderPreSql.append(",? ?");
        }
        orderPreSql.getValues().add(columnName);
        orderPreSql.getValues().add(orderBy);
        return this;
    }

    private interface OrderBy {
        String ASC = "asc";
        String Desc = "desc";
    }

    @Override
    public PreSql getEndSql() {
        var preSql = getBasicPreSql();
        if (!preSql.isEnd()) {
            if (whereSQL != null) {
                preSql.append(whereSQL.getEndSql());
            }
            preSql.append(Optional.ofNullable(orderPreSql));
            preSql.isEnd(true);
        }
        return preSql;
    }
    protected Object getResult(PreparedStatement ps) throws SQLException {
        return switch (getResultType()) {
            case Cell: {
                var resultSet = ps.executeQuery();
                if (resultSet.next()) {
                    yield resultSet.getObject(1);
                }
                yield null;
            }
            case RecordOne: {
                var resultSet = ps.executeQuery();
                if (resultSet.next()) {
                    var metaData = resultSet.getMetaData();
                    var columnCount = metaData.getColumnCount();
                    Map<String, Object> map = new StringMap(columnCount);
                    /*下标从1开始*/
                    for (int i = 1; i <= columnCount; i++) {
                        var columnName = metaData.getColumnName(i);
                        map.put(columnName, resultSet.getObject(i));
                    }
                    Class<T> returnClass = getReturnJavaType();
                    if (returnClass == null) {
                        yield map;
                    }
                    yield JsonUtil.map2Model(map, returnClass);
                }
                yield null;
            }
            case RecordList: {
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
                Class returnClass = getReturnJavaType();
                if (returnClass == null) {
                    yield mapList;
                }
                yield JsonUtil.model2Model(mapList, List.class, returnClass);
            }
            default: throw new SQLException("sql类型异常！");
        };
    }
}
