/**
 * @author chenzy
 * @description
 * @since 2020/4/29
 */module all {
    requires javafx.graphics;
    requires javafx.controls;
    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.databind;
    requires druid;
    requires org.mybatis;
    requires slf4j.api;
    requires jedis;
    requires java.sql;
    requires javafx.fxml;
    requires cglib;
    requires java.desktop;
    requires javafx.media;
    requires org.yaml.snakeyaml;
    requires org.apache.commons.lang3;
    requires java.naming;
//    opens com.czy.fx.test10_AnchorPane;
    opens com.czy.swing.view;
}