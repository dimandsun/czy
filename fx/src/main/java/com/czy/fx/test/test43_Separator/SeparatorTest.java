package com.czy.fx.test.test43_Separator;

import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Separator;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * @author chenzy
 * @description
 * @since 2020/5/17
 */
public class SeparatorTest extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        var box = new VBox();
        var btn = new Button("1");
        var btn2 = new Button("2");
        var separator=new Separator();
//        separator.setValignment(VPos.CENTER);
        separator.setHalignment(HPos.CENTER);
        box.getChildren().addAll(btn,separator,btn2);

        primaryStage.setScene(new Scene(box));
        primaryStage.show();
    }
}
