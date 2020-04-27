package com.czy.fx.test6_scene
        ;

import javafx.application.Application;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

/**
 * @author chenzy
 * @description
 * @since 2020/4/27
 */
public class SceneTest extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        var btn = new Button("按钮");
        btn.setPrefWidth(10);
        btn.setPrefHeight(100);
        String path=getClass().getClassLoader().getResource("com/czy/fx/img/草莓.jpg").toExternalForm();
        btn.setCursor(Cursor.cursor(path));

        var btn2 = new Button("百度一下");
        btn2.setOnAction(actionEvent -> {
            getHostServices().showDocument("www.baidu.com");
        });

        Group group = new Group();
        group.getChildren().add(btn);
        group.getChildren().add(btn2);
        var scene = new Scene(group);
        scene.setCursor(Cursor.CLOSED_HAND);
        stage.setScene(scene);
        stage.show();
    }
}
