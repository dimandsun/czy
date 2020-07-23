package com.czy.jdbc.exception;

import java.sql.SQLException;

/**
 * @author chenzy
 * @date 2020-07-23
 */
public class DaoException extends SQLException {
    public DaoException(String msg) {
        super(msg);
    }
}
