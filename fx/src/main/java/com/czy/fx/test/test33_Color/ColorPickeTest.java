package com.czy.fx.test.test33_Color;

import com.czy.fx.test.FXUtil;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.ColorPicker;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * @author chenzy
 * @description
 * @since 2020-05-15
 */
public class ColorPickeTest extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        var cp = new ColorPicker(Color.BURLYWOOD);
        var ap=new AnchorPane();
        cp.valueProperty().addListener((observable, oldValue, newValue) -> {
            FXUtil.setColor(ap,newValue);
        });
        cp.setPromptText("123");
//        cp.promptTextProperty()

        ap.getChildren().add(cp);
        ap.setPrefWidth(100);
        ap.setPrefHeight(100);
        stage.setScene(new Scene(ap));
        stage.show();
    }
}
