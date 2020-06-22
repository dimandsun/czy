/**
 * @author chenzy
 * @date 2020-06-22
 */module question.bank {
    requires com.czy.fx;
    requires javafx.graphics;
    requires javafx.fxml;
    requires javafx.controls;
    requires com.czy.util;
    requires com.czy.core;
    requires com.fasterxml.jackson.annotation;
    exports com.czy.question.client.view;
    exports com.czy.question.server.model.table;
    opens com.czy.question.client.view;
}