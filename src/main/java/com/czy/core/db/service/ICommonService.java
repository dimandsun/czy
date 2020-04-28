package com.czy.core.db.service;

import java.util.List;
import java.util.Map;

/**
 * @author chenzy
 * @description
 * @since 2020-04-24
 */
public interface ICommonService {
    <Bean> Integer insert(Bean bean);

    <Bean> Boolean update(Bean setBean, Bean whereBean);

    <Bean> Bean getOne(Bean whereBean);

    Map<String, Object> getOneMap(Map<String, Object> wherePar, String tableName);

    <Bean> Bean getOnePart(Bean whereBean, String... columns);

    Map<String, Object> getOnePartMap(Map<String, Object> wherePar, String tableName, String... columns);

    <Bean> List<Bean> getListBean(Bean whereBean, String... columns);

    List<Map<String, Object>> getListMap(Map<String, Object> wherePar, String tableName, String... columns);

    <Bean> Boolean delete(Bean bean);

    <Bean> Boolean delete(Map<String, Object> wherePar, String tableName);
}
