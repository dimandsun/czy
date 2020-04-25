package com.czy.core.db.dao;

import com.czy.core.annotation.Dao;

import java.util.Map;

/**
 * @author chenzy
 * @description
 * @since 2020-04-24
 */
@Dao
public interface ICommonDao {
    Integer insert(Map<String, Object> par, String tableName);
}
