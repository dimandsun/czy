/**
 * @author chenzy
 * @since 2020-06-17
 */module
car_client {
    requires com.czy.core;
    requires com.czy.fx;
    requires javafx.graphics;
    requires javafx.fxml;
    requires com.czy.util;
    requires javafx.controls;
    requires cglib;
    requires com.fasterxml.jackson.annotation;
    exports com.czy.car_client.view;
    exports com.czy.car_client.view.user;
    opens com.czy.car_client.view.user;
}