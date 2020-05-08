package com.czy.fx.test.test12_BorderPane;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * @author chenzy
 * @since 2020/5/8
 * @description 方位布局
 */
public class BorderPaneTest extends Application {


    @Override
    public void start(Stage primaryStage) throws Exception {
        var bp=new BorderPane();
        bp.setStyle("-fx-background-color: #FFFAF0");

        AnchorPane ap1 = new AnchorPane();
        ap1.setStyle("-fx-background-color: #668B8B");
        ap1.setPrefWidth(100);
        ap1.setPrefHeight(100);

        AnchorPane ap2 = new AnchorPane();
        ap2.setStyle("-fx-background-color: #000000");
        ap2.setPrefWidth(100);
        ap2.setPrefHeight(100);

        AnchorPane ap3 = new AnchorPane();
        ap3.setStyle("-fx-background-color: #0000FF");
        ap3.setPrefWidth(100);
        ap3.setPrefHeight(100);

        AnchorPane ap4 = new AnchorPane();
        ap4.setStyle("-fx-background-color: #00FF00");
        ap4.setPrefWidth(100);
        ap4.setPrefHeight(100);

        AnchorPane ap5 = new AnchorPane();
        ap5.setStyle("-fx-background-color: #FFA500");
        /*上下左右子控件没有设置宽高时，中间控件会默认填充全部空间*/
        bp.setTop(ap1);
        bp.setBottom(ap2);
        bp.setLeft(ap3);
        bp.setRight(ap4);
        bp.setCenter(ap5);

        primaryStage.setScene(new Scene(bp));
        primaryStage.show();
    }
}
