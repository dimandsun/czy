package com.czy.jdbc.exception;

import java.sql.SQLException;

/**
 * @author chenzy
 * @date 2020-07-16
 */
public class ConnectionClosedException extends SQLException {
    public ConnectionClosedException(String msg) {
        super(msg);
    }
}
