package com.czy.fx.test10_AnchorPane;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;

/**
 * @author chenzy
 * @description
 * @since 2020/4/28
 */
public class AnchorPaneTest extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        var btn =new Button("按钮1");
        var btn2 =new Button("按钮2");
        var btn3 =new Button("按钮3");
        var btn4 =new Button("按钮4");
        var anchorPane=new AnchorPane();
        Double pianYiLiang = 20.0;
        AnchorPane.setTopAnchor(btn,pianYiLiang);
        AnchorPane.setBottomAnchor(btn2,pianYiLiang);
        AnchorPane.setLeftAnchor(btn3,pianYiLiang);
        AnchorPane.setRightAnchor(btn4,pianYiLiang);
        btn.setManaged(false);//父组件不能管理此按钮，即不占用位置
        btn.setVisible(true);//不可见
        btn.setOpacity(1);//透明
        anchorPane.setPadding(new Insets(pianYiLiang));//内边距
        anchorPane.getChildren().addAll(btn,btn2,btn3,btn4);
        stage.setScene(new Scene(anchorPane));
        stage.show();
    }
}
