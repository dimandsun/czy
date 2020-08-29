package com.czy.jdbc;

import com.czy.jdbc.exception.SQLParseException;
import com.czy.jdbc.sql.SQLFactory;
import com.czy.jdbc.sql.SelectSQLBuilder;
import com.czy.jdbc.sql.UpdateSQLBuilder;
import com.czy.jdbc.sql.WhereSQL;
import com.czy.jdbc.sql.enums.ResultTypeEnum;
import com.czy.log.Log;
import com.czy.log.LogFactory;
import com.czy.util.enums.ResCodeEnum;
import com.czy.util.model.ResultVO;
import com.czy.util.model.StringMap;

import java.sql.SQLException;
import java.util.List;

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
        try {
            Object result = SQLFactory.insert(tableName, columnMap).exec();
            System.out.println(result);
            return true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }

    }

    /**
     * 执行一个sql语句，修改
     */
    public static Boolean update(String tableName, StringMap setMap, StringMap whereMap) {
        try {
            Object result = SQLFactory.update(tableName, setMap, whereMap).exec();
            System.out.println(result);
            return true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }
    }

    /**
     * 执行一个sql语句，删除
     */
    public static Boolean delete(String tableName, StringMap whereMap) {
        try {
            Object result = SQLFactory.delete(tableName, whereMap).exec();
            System.out.println(result);
            return true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }
    }

    /**
     * 执行一个sql语句，查询
     */
    public static List<StringMap> select(String tableName, StringMap whereMap) {
        try {
            Object result = SQLFactory.select(tableName, whereMap).exec();
            System.out.println(result);
            return (List<StringMap>) result;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return null;
        }
    }

    public <T> ResultVO<T> getData(String tableName, String columns, WhereSQL whereSQL, ResultTypeEnum resultTypeEnum, Class<T> resultClass, Integer limitSize){
        var sqlBuilder = SQLFactory.select(tableName, resultClass);
        sqlBuilder.setResultType(resultTypeEnum);
        if (limitSize!=null&&limitSize>0){
            sqlBuilder.limit(limitSize);
        }
        sqlBuilder.selectColumns(columns).where(whereSQL);
        try {
            T data=sqlBuilder.exec();
            if (data==null){
                return new ResultVO<>(ResCodeEnum.DBWarn,"没有查到数据");
            }
            return new ResultVO<>(data);
        } catch (SQLException e) {
            e.printStackTrace();
            return new ResultVO<>(ResCodeEnum.DBError);
        }
    }

    public <T> ResultVO<List<T>> getListData(String tableName, String columns, WhereSQL whereSQL, Class<T> resultClass, Integer limitSize) {
        SelectSQLBuilder<List<T>> sqlBuilder = SQLFactory.selectList(tableName,resultClass);
        if (limitSize!=null&&limitSize>0){
            sqlBuilder.limit(limitSize);
        }
        sqlBuilder.selectColumns(columns).where(whereSQL);
        try {
            List<T> data=sqlBuilder.exec();
            if (data==null||data.isEmpty()){
                return new ResultVO<>(ResCodeEnum.DBInfo,"没有查到数据");
            }
            return new ResultVO<>(data);
        } catch (SQLException e) {
            e.printStackTrace();
            return new ResultVO<>(ResCodeEnum.DBError);
        }
    }
    public ResultVO<Integer> update(String tableName, StringMap<Object> setMap, String setSQLText, WhereSQL whereSQL) {
        UpdateSQLBuilder sqlBuilder =SQLFactory.update(tableName,setMap).addSetSQL(setSQLText);
        sqlBuilder.where(whereSQL);
        return update(sqlBuilder);
    }

    public ResultVO<Integer> update(UpdateSQLBuilder sqlBuilder) {
        try {
            Integer staus=sqlBuilder.exec();
            return new ResultVO<>(staus);
        } catch (SQLException e) {
            e.printStackTrace();
            return new ResultVO<>(ResCodeEnum.DBError);
        }
    }
}
