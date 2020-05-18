package com.czy.fx.test.test44_Point2D;

import com.czy.fx.test.FXUtil;
import com.czy.util.StringUtil;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import static com.czy.util.StringUtil.println;

/**
 * @author chenzy
 * @description
 * @since 2020/5/17
 */
public class Point2DTest extends Application {


    @Override
    public void start(Stage primaryStage) throws Exception {
        var anchorPane = new AnchorPane();
        var rectangle = new Rectangle();
        rectangle.setWidth(100);
        rectangle.setHeight(100);
        var box = new HBox();
        FXUtil.getButtonList("1");
        var btn = new Button();

        box.getChildren().addAll(btn);
        box.getChildren().addAll(rectangle);
        anchorPane.getChildren().addAll(box);
        btn.setOnAction(event -> {
            println("btn setPrefWidth前-----getWidth:{},getPrefWidth:{}", btn.getWidth(), btn.getPrefWidth());
            btn.setPrefWidth(18);
            println("btn setPrefWidth后-----getWidth:{},getPrefWidth:{}", btn.getWidth(), btn.getPrefWidth());
            println("box-----getWidth:{},getPrefWidth:{}", box.getWidth(), box.getPrefWidth());
        });

        box.setPrefWidth(100);
        primaryStage.setScene(new Scene(anchorPane));
        primaryStage.show();
    }
}
