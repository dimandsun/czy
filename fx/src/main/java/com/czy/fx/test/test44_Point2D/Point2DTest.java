package com.czy.fx.test.test44_Point2D;

import com.czy.fx.test.FXUtil;
import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import static com.czy.util.text.StringUtil.println;

/**
 * @author chenzy
 * 
 * @since 2020/5/17
 */
public class Point2DTest extends Application {

    private Double x=0d;
    private Double y=0d;
    @Override
    public void start(Stage primaryStage) throws Exception {
        var anchorPane = new AnchorPane();
        var rectangle = new Rectangle();
        rectangle.setWidth(100);
        rectangle.setHeight(100);

        var box = new HBox();
        FXUtil.getButtonList("1");
        var person = new Button("陈志源");
        var npc = new Button("陈晓云");
        box.getChildren().addAll(npc, person);
        anchorPane.getChildren().addAll(npc,person);
        person.setOnAction(event -> {
            println("btn setPrefWidth前-----getWidth:{},getPrefWidth:{}", person.getWidth(), person.getPrefWidth());
            person.setPrefWidth(18);
            println("btn setPrefWidth后-----getWidth:{},getPrefWidth:{}", person.getWidth(), person.getPrefWidth());
            println("box-----getWidth:{},getPrefWidth:{}", box.getWidth(), box.getPrefWidth());
        });

        box.setPrefWidth(1000);
        box.setPrefHeight(500);
        var scene = new Scene(anchorPane);
        primaryStage.setScene(scene);
        primaryStage.show();
        scene.setOnKeyPressed(event -> {
            person.getLayoutBounds();
            println("{}按下前，person坐标：({},{})",event.getCode(),person.getLayoutX(),person.getLayoutY());
            switch (event.getCode()) {
                case A:
                    x-=10;
                    break;
                case W:
                    y-=10;
                    break;
                case D:
                    x+=10;
                    break;
                case S:
                    y+=10;
                    break;
                default:
                    break;
            }
            if (x>0){
                person.setLayoutX(x);
            }
            if (y>0){
                person.setLayoutY(y);
            }
            println("{}按下后，person坐标：({},{})",event.getCode(),person.getLayoutX(),person.getLayoutY());
        });
        person.layoutXProperty().addListener((observable, oldValue, newValue) -> {
            System.out.println("x轴改变:"+newValue);
        });
        person.layoutYProperty().addListener((observable, oldValue, newValue) -> {
            System.out.println("y轴改变:"+newValue);
        });
        /*组件边界：没有加特效之前的边界*/
        var bounds = person.getLayoutBounds();
        /*最小坐标：最小始终是：(0,0,0)*/
        println("最小坐标({},{},{}):", bounds.getMinX(), bounds.getMinY(), bounds.getMinZ());

        /*包括特效的边界*/
        person.getBoundsInLocal();

        /*获得相对于父组件的坐标*/
        var localToParent = person.localToParent(bounds);
        println("在父组件最小坐标({},{},{}):", localToParent.getMinX(), localToParent.getMinY(), localToParent.getMinZ());
        /*获得相对于场景中的坐标*/
        var localToScene = person.localToScene(bounds);
        println("在场景最小坐标({},{},{}):", localToScene.getMinX(), localToScene.getMinY(), localToScene.getMinZ());
        /*获得相对于屏幕的坐标*/
        var localToScreen = person.localToScreen(bounds);
        println("在屏幕的坐标({},{},{}):", localToScreen.getMinX(), localToScreen.getMinY(), localToScreen.getMinZ());

//        btn.parentToLocal(localToParent);

    }

    private <T extends Node>void movXInScene(T node,Double x){
        if (x==null||x.equals(0)){
            return;
        }
        var bounds=node.localToScene(node.getBoundsInLocal());
        if (x>0){

        }
        var scene=node.getScene();
        bounds.getMaxX();


    }
    private <T extends Node>void movY(T node,Double y){

    }
}
