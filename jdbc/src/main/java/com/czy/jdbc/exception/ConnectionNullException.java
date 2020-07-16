package com.czy.jdbc.exception;

import java.sql.SQLException;

/**
 * @author chenzy
 * @date 2020-07-16
 */
public class ConnectionNullException extends SQLException {
    public ConnectionNullException(String msg) {
        super(msg);
    }
}
