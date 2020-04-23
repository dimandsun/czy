package com.czy.core.test.dao;


import com.czy.core.annotation.Dao;
import com.czy.util.model.MyMap;

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
