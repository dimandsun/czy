package test73_Transform;

import com.czy.fx.test.FXUtil;
import javafx.application.Application;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;

/**
 * @author chenzy
 * @since 2020-06-12
 * 旋转
 */
public class RotateTest extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {

        var root = new AnchorPane();
        var anchorPane = new AnchorPane();
        var btn = new Button("旋转");
        var btn2 = new Button("按钮");
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

        btn.setOnAction(event -> {
            //参数1顺时针旋转角度正，逆时针转负数，参数二三是旋转原点
            btn.getTransforms().add(new Rotate(45,0,0));

            //旋转方式2：旋转45度
//            btn.setRotate(45);
            var bounds=btn.getLayoutBounds();
            var bounds1=btn.localToParent(bounds);
        });


        root.getChildren().add(anchorPane);





        FXUtil.setDefaultValue(primaryStage,root);
        AnchorPane.setTopAnchor(anchorPane,100d);
        AnchorPane.setLeftAnchor(anchorPane,100d);
    }
}
