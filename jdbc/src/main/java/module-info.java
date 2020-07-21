/**
 * @author chenzy
 * @date 2020-07-21
 */module com.czy.jdbc {
    requires java.sql;
    requires com.czy.util;
    requires com.czy.log;
    exports com.czy.jdbc.pool;
    exports com.czy.jdbc;
}