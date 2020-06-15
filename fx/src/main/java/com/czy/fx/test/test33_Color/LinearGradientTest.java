package com.czy.fx.test.test33_Color;

import com.czy.fx.test.FXUtil;
import javafx.application.Application;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.*;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.util.ArrayList;

/**
 * @author chenzy
 * @since 2020-06-15
 */
public class LinearGradientTest extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        var gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        var btn = new Button();
        var width = 300;
        var height = 300;
        for (int i = 0; i < 6; i++) {
            var rectangle = new Rectangle(width, height);
            rectangle.setFill(Paint.valueOf("#EDEDED"));
            gridPane.add(rectangle, i, 0);
        }
        var box = new HBox();
        box.getChildren().addAll(new Circle(150), new Polygon(150, 0, 0, 300, 300, 300));
        var anchorPane = new AnchorPane();
        anchorPane.getChildren().addAll(gridPane, box);

        var image=new Image("girl2.jpg");

        var list = new ArrayList<Paint>();
        list.add(new LinearGradient(0,0,width,height,false, CycleMethod.NO_CYCLE,new Stop[]{
                new Stop(0,Color.valueOf("#3238b0"))
                ,new Stop(0.5,Color.valueOf("#e7225e"))
                ,new Stop(1,Color.valueOf("#35da63"))
        }));
        list.add(new ImagePattern(image,0,0,0.5,0.5,true));
        list.add(new ImagePattern(image,0,0,100,100,false));
        list.add(new ImagePattern(image,50,50,0.5,0.5,false));
        for (int i = 0; i < list.size(); i++) {
            ((Rectangle)gridPane.getChildren().get(i)).setFill(list.get(i));
        }

        var circle=(Circle) box.getChildren().get(0);
        circle.setFill(new ImagePattern(image,0,0,100,100,false));
        circle.setFill(new ImagePattern(image,50,50,100,100,false));
        var polygon=(Polygon) box.getChildren().get(1);
        polygon.setFill(new ImagePattern(image,0,0,100,100,false));

        FXUtil.setDefaultValue(primaryStage, anchorPane,1600d,1000d);
        AnchorPane.setTopAnchor(box, gridPane.getHeight() + 10);
    }
}
