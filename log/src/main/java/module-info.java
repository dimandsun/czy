/**
 * @author chenzy
 * @date 2020-07-17
 */
module com.czy.log {
    requires java.logging;
    requires com.czy.util;
    exports com.czy.log;
    opens com.czy.log;
    requires com.fasterxml.jackson.annotation;

}