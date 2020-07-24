package db.dao;

import java.util.List;
import java.util.Map;

/**
 * @author chenzy
 * @since 2020/7/25
 */
public interface ICommonDao {
    Integer insert(Map<String, Object> par, String tableName);

    Integer update(Map<String, Object> setPar, Map<String, Object> wherePar, String tableName);

    Integer delete(Map<String, Object> par, String tableName);

    List<Map<String, Object>> getList(Map<String, Object> par, String tableName);

    Map<String, Object> getOne(Map<String, Object> wherePar, String tableName);

    Map<String, Object> getOnePart(String[] columns, Map<String, Object> wherePar, String tableName);

    List<Map<String, Object>> getListPart(String[] columns, Map<String, Object> wherePar, String tableName);

}
