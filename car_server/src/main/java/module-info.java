/**
 * @author chenzy
 * @since 2020-06-15
 */
module car_server {
    requires com.czy.core;
    requires com.czy.util;
    requires java.desktop;
    requires cglib;
    requires com.fasterxml.jackson.databind;
    exports com.czy.car_server.controller;
    exports com.czy.car_server.service.impl;
    exports com.czy.car_server.model.table;
    opens com.czy.car_server.service.impl;
    opens com.czy.car_server.controller;
}