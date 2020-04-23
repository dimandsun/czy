package com.czy.frame.test.dao;

import com.czy.frame.core.annotation.Dao;
import com.czy.frame.model.MyMap;

import java.util.Map;

/**
 * @author chenzy
 * @description
 * @since 2020-03-31
 */
@Dao
public interface ITestDao {
    Map<String,Object> get(MyMap parMap);

}
