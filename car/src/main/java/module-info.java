/**
 * @author chenzy
 * @since 2020-06-15
 */
module car {
    requires javafx.graphics;
    requires javafx.controls;
    requires javafx.media;
    requires javafx.fxml;
    requires com.czy.core;
    requires com.czy.util;
    requires java.desktop;
    requires javafx.swing;
    requires javafx.web;
    requires com.czy.fx;
    exports com.czy.car.view.user;
    exports com.czy.car.view;
}