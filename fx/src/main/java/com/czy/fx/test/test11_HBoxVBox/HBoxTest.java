package com.czy.fx.test.test11_HBoxVBox;

import com.czy.fx.test.FXUtil;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

/**
 * @author chenzy
 * @since 2020/5/3
 * @description HBox是水平布局，VBox是垂直布局，两者用法一样
 */
public class HBoxTest extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        var ap =new AnchorPane();
        ap.setStyle("-fx-background-color: #aeeeee");

        var hBox = new HBox();
        hBox.setPrefWidth(400);
        hBox.setPrefHeight(400);
        hBox.setPadding(new Insets(10));
        /*子控件的间距*/
        hBox.setSpacing(10);

        /**/
        hBox.setBackground(new Background(new BackgroundFill(Paint.valueOf("#aa1100"),new CornerRadii(20),new Insets(1))));

        var buttonList= FXUtil.getButtonList("btn1","btn2","btn3","btn4");
        /*指定子控件外边距*/
        hBox.setMargin(buttonList.get(0),new Insets(20));
        /*对齐方式:向下居中等等*/
        hBox.setAlignment(Pos.BOTTOM_CENTER);
        hBox.getChildren().addAll(buttonList);
        hBox.setStyle("-fx-background-color: #e066ff");
        ap.getChildren().add(hBox);
        primaryStage.setScene(new Scene(ap));
        primaryStage.show();
    }

}
