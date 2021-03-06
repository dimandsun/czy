package com.czy.jdbc.sql;

import com.czy.jdbc.sql.enums.ResultTypeEnum;
import com.czy.util.ClassUtil;
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
public class SelectSQLBuilder<T> extends SQLBuilder<T> implements WhereColumnValues {
    private WhereSQL whereSQL;
    private PreSql orderPreSql;
    private List<String> columnList;

    public SelectSQLBuilder(PreSql preSql, ResultTypeEnum returnType) {
        super(preSql, returnType);
        columnList = new ArrayList<>();
    }
    public SelectSQLBuilder selectColumns(String... columns) {
        if (columns == null) {
            return this;
        }
        for (int i = 0; i < columns.length; i++) {
            String temp = columns[i];
            if (StringUtil.isBlank(temp)) {
                continue;
            } else if (temp.contains(",")) {
                String[] temps = temp.split(",");
                selectColumns(temps);
            } else {
                columnList.add(temp);
            }
        }
        return this;
    }
    public WhereSQL where(WhereSQL whereSQL){
        this.whereSQL=whereSQL;
        return this.whereSQL;
    }
    public WhereSQL where() {
        return whereSQL == null ? where(WhereSQL.newInstance()) : whereSQL;
    }
    public SelectSQLBuilder limit(int size) {
        if (size<1){
            return this;
        }else if (size==1){
            setResultType(ResultTypeEnum.RecordOne);
        }else{
            setResultType(ResultTypeEnum.RecordList);
        }
        return this;
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
            orderPreSql.addSQLText(",? ?");
        }
        orderPreSql.addPar(columnName);
        orderPreSql.addPar(orderBy);
        return this;
    }
    @Override
    public WhereSQL whereColumnValues(Map columnMap) {
        return where().equal(columnMap);
    }

    private interface OrderBy {
        String ASC = "asc";
        String Desc = "desc";
    }

    @Override
    public PreSql beforeExec() {
        var preSql = getPreSql();
        if (!preSql.isEnd()) {
            if (!columnList.isEmpty()){
                String temp=columnList.toString();
                preSql.replace("\\$\\[columns\\]", temp.substring(1, temp.length() - 1));
            }
            if (whereSQL != null) {
                preSql.append(whereSQL.getEndSql());
            }
            preSql.append(Optional.ofNullable(orderPreSql));
            if (ResultTypeEnum.RecordOne.equals(getResultType())){
//                preSql.append(" limit 1");
                preSql.replace("(?i)select ","select top 1 ");
            }
            preSql.isEnd(true);
        }
        return preSql;
    }
    protected T getResult(PreparedStatement ps) throws SQLException {
        return switch (getResultType()) {
            case Cell: {
                var resultSet = ps.executeQuery();
                if (resultSet.next()) {
                    yield (T)resultSet.getObject(1);
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
                        yield (T)map;
                    }
                    if (ClassUtil.isBasicDataType(returnClass)) {
                        Object value=map.values().iterator().next();
                        yield value==null?null:StringUtil.obj2BasicType(value, returnClass);
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
                    yield (T)mapList;
                }
                yield (T)JsonUtil.model2Model(mapList, List.class, returnClass);
            }
            default: throw new SQLException("sql类型异常！");
        };
    }
}
