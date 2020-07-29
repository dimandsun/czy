package test73_Transform;

import com.czy.fx.test.FXUtil;
import javafx.application.Application;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.transform.Shear;
import javafx.stage.Stage;

/**
 * @author chenzy
 * @since 2020-06-12
 * 变形
 */
public class ShearTest extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {

        var root = new AnchorPane();
        var anchorPane = new AnchorPane();
        var btn = new Button("1");
        var btn2 = new Button("变形");
        anchorPane.setPrefWidth(600);
        anchorPane.setPrefHeight(600);
        anchorPane.setStyle("-fx-background-color: #ffb5c5");
        anchorPane.getChildren().addAll(btn,btn2);
        btn.setPrefWidth(100);
        btn.setPrefHeight(200);
        btn2.setPrefWidth(100);
        btn2.setPrefHeight(200);

        btn2.setOnAction(event -> {
            //参数三四是原点
            btn2.getTransforms().add(new Shear(0.5,0.5,0,0));
        });

        btn.setOnAction(event -> {
        });

        root.getChildren().add(anchorPane);





        FXUtil.setDefaultValue(primaryStage,root);
        AnchorPane.setTopAnchor(anchorPane,100d);
        AnchorPane.setLeftAnchor(anchorPane,100d);
    }
}
