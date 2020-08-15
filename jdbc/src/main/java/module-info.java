/**
 * @author chenzy
 * @date 2020-07-21
 */module com.czy.jdbc {
    requires java.sql;
    requires com.czy.util;
    requires com.czy.log;
    requires com.fasterxml.jackson.annotation;
    exports com.czy.jdbc.pool;
    exports com.czy.jdbc;
    exports com.czy.jdbc.sql.annotation;
    exports com.czy.jdbc.sql.enums;
    exports com.czy.jdbc.sql;
}