package com.czy.jdbc.sql;

import java.util.Map;

/**
 * @author chenzy
 * @date 2020-07-25
 */
public interface WhereColumnValues {
    /**
     *
     * @param columnMap
     */
    WhereSQL whereColumnValues(Map columnMap);
}
