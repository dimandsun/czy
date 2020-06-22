package com.czy.fx.test.test73_Transform;

import com.czy.fx.test.FXUtil;
import javafx.application.Application;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.transform.Scale;
import javafx.stage.Stage;

import static com.czy.util.text.StringUtil.println;

/**
 * @author chenzy
 * @since 2020-06-12
 * 缩放
 */
public class ScaleTest extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {

        var root = new AnchorPane();
        var anchorPane = new AnchorPane();
        var btn = new Button("缩小");
        var btn2 = new Button("放大");
        anchorPane.setPrefWidth(600);
        anchorPane.setPrefHeight(600);
        anchorPane.setStyle("-fx-background-color: #ffb5c5");
        anchorPane.getChildren().addAll(btn,btn2);
        btn.setLayoutX(100);//x轴正位移100
        btn.setLayoutY(100);//y轴正位移100
        btn.setPrefWidth(100);
        btn.setPrefHeight(100);
        btn2.setPrefWidth(100);
        btn2.setPrefHeight(100);

        btn2.setOnAction(event -> {
            //方式一、参数1 2为缩放比例，参数3 4为缩放原点
            btn2.getTransforms().add(new Scale(2,2,0,0));
            //方式二、
//            btn2.setScaleX(2);
//            btn2.setScaleY(2);
            println("btn2虚假的宽高width:{},height:{}", btn2.getPrefWidth(),btn2.getPrefHeight());
            var bounds=btn2.getLayoutBounds();
            var bounds1=btn2.localToParent(bounds);
            println("btn2真正的宽高width:{},height:{}",bounds1.getWidth(),bounds1.getHeight());

            var scale=new Scale(2,2,0,0);

            //这是什么意思
            scale.transform(0.5,0.5);

        });

        btn.setOnAction(event -> {
            btn.getTransforms().add(new Scale(0.5,0.5,0,0));
        });

        root.getChildren().add(anchorPane);





        FXUtil.setDefaultValue(primaryStage,root);
        AnchorPane.setTopAnchor(anchorPane,100d);
        AnchorPane.setLeftAnchor(anchorPane,100d);
    }
}
