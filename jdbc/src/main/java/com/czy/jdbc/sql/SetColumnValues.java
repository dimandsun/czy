package com.czy.jdbc.sql;

import com.czy.util.model.StringMap;

import java.util.Map;

/**
 * @author chenzy
 * @date 2020-07-25
 */
public interface SetColumnValues {
    /**
     *
     * @param columnMap
     */
    <T extends SQLBuilder> T setColumnValues(Map columnMap);
}
