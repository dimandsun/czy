package com.czy.core.db.dao;

import com.czy.core.annotation.bean.Dao;
import java.util.List;
import java.util.Map;

/**
 * @author chenzy
 * 
 * @since 2020-04-24
 */
@Dao
public interface ICommonDao {
    Integer insertFQ(Map<String, Object> parMap, String tableName);
    Integer insert(Map<String, Object> parMap, String tableName);

    Integer update(Map<String, Object> setPar, Map<String, Object> wherePar, String tableName);

    Map<String, Object> getOnePart(String[] columns, Map<String, Object> wherePar, String tableName);

    Map<String, Object> getOne(Map<String, Object> wherePar, String tableName);

    List<Map<String, Object>> getListPart(String[] columns, Map<String, Object> wherePar, String tableName);

    List<Map<String, Object>> getList(Map<String, Object> wherePar, String tableName);

    Integer delete(Map<String, Object> wherePar, String tableName);
}
