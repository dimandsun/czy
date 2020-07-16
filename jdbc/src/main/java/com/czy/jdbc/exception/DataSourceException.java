package com.czy.jdbc.exception;

import java.sql.SQLException;

/**
 * @author chenzy
 * @date 2020-07-16
 */
public class DataSourceException extends SQLException {
    public DataSourceException(String msg) {
        super(msg);
    }
}
