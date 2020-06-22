package com.czy.core.db.dao;

import com.czy.core.annotation.bean.Dao;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author chenzy
 * 
 * @since 2020-04-24
 */
@Dao
public interface ICommonDao {
    Integer insertFQ(@Param("parMap") Map<String, Object> parMap, @Param("tableName") String tableName);
    Integer insert(@Param("parMap") Map<String, Object> parMap, @Param("tableName")String tableName);

    Integer update(@Param("setPar")Map<String, Object> setPar, @Param("wherePar")Map<String, Object> wherePar,@Param("tableName") String tableName);

    Map<String, Object> getOnePart(@Param("columns")String[] columns,@Param("wherePar")Map<String, Object> wherePar,@Param("tableName") String tableName);

    Map<String, Object> getOne(@Param("wherePar")Map<String, Object> wherePar,@Param("tableName") String tableName);

    List<Map<String, Object>> getListPart(@Param("columns")String[] columns, @Param("wherePar")Map<String, Object> wherePar, String tableName);

    List<Map<String, Object>> getList(@Param("wherePar")Map<String, Object> wherePar,@Param("tableName") String tableName);

    Integer delete(@Param("wherePar") Map<String, Object> wherePar,@Param("tableName") String tableName);
}
