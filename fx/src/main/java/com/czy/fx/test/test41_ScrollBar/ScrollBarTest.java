package com.czy.fx.test.test41_ScrollBar;

import com.czy.fx.test.FXUtil;
import javafx.application.Application;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.ScrollBar;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * @author chenzy
 * @since 2020/5/17
 *  滚动条
 */
public class ScrollBarTest extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        var anchorPane = new AnchorPane();

        var box = new VBox();

        box.getChildren().addAll(FXUtil.getButtonList("1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11"));
        var scrollBar = new ScrollBar();
        /*滚动条上按钮的长度*/
        scrollBar.setVisibleAmount(50);
        scrollBar.setOrientation(Orientation.VERTICAL);
        scrollBar.valueProperty().addListener((observable, oldValue, newValue) -> {
            System.out.println(newValue);
            box.setLayoutY(-newValue.doubleValue());
        });
        /*每滚动一次的长度*/
        scrollBar.setUnitIncrement(20);
        scrollBar.setBlockIncrement(100);
        anchorPane.getChildren().addAll(scrollBar, box);
        AnchorPane.setLeftAnchor(scrollBar, 100d);
        primaryStage.setWidth(1000);
        primaryStage.setHeight(200);
        primaryStage.setScene(new Scene(anchorPane));
        primaryStage.show();
        scrollBar.setPrefHeight(box.getHeight());
    }
}
