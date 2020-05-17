package com.czy.fx.test.test44_Point2D;

import com.czy.fx.test.FXUtil;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

/**
 * @author chenzy
 * @description
 * @since 2020/5/17
 */
public class Point2DTest extends Application {


    @Override
    public void start(Stage primaryStage) throws Exception {
        var anchorPane = new AnchorPane();
        var rectangle=new Rectangle();
        rectangle.setWidth(100);
        rectangle.setHeight(100);
        var box = new HBox();
        box.getChildren().addAll(FXUtil.getButtonList("1"));
        box.getChildren().addAll(rectangle);
        anchorPane.getChildren().addAll(box);
        primaryStage.setScene(new Scene(anchorPane));
        primaryStage.show();
    }
}
