package com.czy.fx.test.test73_Transform;

import com.czy.fx.test.FXUtil;
import javafx.application.Application;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.transform.Translate;
import javafx.stage.Stage;

import static com.czy.util.text.StringUtil.println;

/**
 * @author chenzy
 * @since 2020-06-12
 */
public class TranslateTest extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        var root = new AnchorPane();
        var anchorPane = new AnchorPane();
        var btn = new Button("按钮");
        var btn2 = new Button("按钮2");
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
            println("btn2在父组件的坐标({},{})",btn2.getLocalToParentTransform().getTx(),btn2.getLocalToParentTransform().getTy());
            println("btn2在场景中的坐标({},{})",btn2.getLocalToSceneTransform().getTx(),btn2.getLocalToSceneTransform().getTy());
        });
        var translate=new Translate(100,100);
        var point2D=translate.transform(100,100);//得到坐标(200,200)
        var point2D1=translate.deltaTransform(100,120);//得到坐标(100,200)
        System.out.println(point2D1);
        btn2.getTransforms().addAll(translate,translate);


        root.getChildren().add(anchorPane);





        FXUtil.setDefaultValue(primaryStage,root);
        AnchorPane.setTopAnchor(anchorPane,100d);
        AnchorPane.setLeftAnchor(anchorPane,100d);
    }
}
