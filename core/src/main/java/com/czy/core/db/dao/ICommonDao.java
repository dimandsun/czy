package com.czy.core.db.dao;

import com.czy.core.annotation.Par;
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
    Integer insertFQ(@Par("parMap") Map<String, Object> parMap, @Par("tableName") String tableName);
    Integer insert(@Par("parMap") Map<String, Object> parMap, @Par("tableName")String tableName);

    Integer update(@Par("setPar")Map<String, Object> setPar, @Par("wherePar")Map<String, Object> wherePar,@Par("tableName") String tableName);

    Map<String, Object> getOnePart(@Par("columns")String[] columns,@Par("wherePar")Map<String, Object> wherePar,@Par("tableName") String tableName);

    Map<String, Object> getOne(@Par("wherePar")Map<String, Object> wherePar,@Par("tableName") String tableName);

    List<Map<String, Object>> getListPart(@Par("columns")String[] columns, @Par("wherePar")Map<String, Object> wherePar, String tableName);

    List<Map<String, Object>> getList(@Par("wherePar")Map<String, Object> wherePar,@Par("tableName") String tableName);

    Integer delete(@Par("wherePar") Map<String, Object> wherePar,@Par("tableName") String tableName);
}
