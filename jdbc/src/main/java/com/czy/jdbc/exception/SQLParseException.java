package com.czy.jdbc.exception;

import java.sql.SQLException;

/**
 * @author chenzy
 * @since 2020/7/24
 */
public class SQLParseException extends SQLException {
    public SQLParseException(String msg) {
        super(msg);
    }
}
