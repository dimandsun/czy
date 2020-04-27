package com.czy.core.db.dao;

import com.czy.core.annotation.Dao;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
 * @author chenzy
 * @description
 * @since 2020-04-24
 */
@Dao
public interface ICommonDao {
    Integer insertFQ(@Param("parMap") Map<String, Object> parMap, @Param("tableName") String tableName);
    Integer insert(@Param("parMap") Map<String, Object> parMap, @Param("tableName")String tableName);
}
