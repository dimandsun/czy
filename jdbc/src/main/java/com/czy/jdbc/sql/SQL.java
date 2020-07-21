package com.czy.jdbc.sql;

import com.czy.util.model.Par;
import com.czy.util.text.StringUtil;

import java.util.List;

/**
 * @author chenzy
 * @date 2020-07-21
 * preSql 预处理sql，即用?代替参数值
 * values 参数值
 */
public interface SQL {
    String getEndPreSql();

    String getBasicPreSql();

    void setPreSql(String preSql);

    List<Object> getValues();
}
