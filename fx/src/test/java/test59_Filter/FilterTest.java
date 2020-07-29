package test59_Filter;

import com.czy.fx.test.FXUtil;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

/**
 * @author chenzy
 * @since 2020-05-28
 * Filter 从容器最外层一直传递到最里面的目标组件
 * EventHandler相反，从子组件传递到父组件
 * event.consume();阻止事件
 *
 *
 */
public class FilterTest extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        var anchorPane = new AnchorPane();

        anchorPane.setPadding(new Insets(10));
        var box = new HBox();

        var btn1 = new Button("按钮");
        var label = new Label("安徽的");

        box.getChildren().addAll(btn1,label);
        box.setAlignment(Pos.CENTER);
        anchorPane.getChildren().add(box);
        label.setStyle("-fx-background-color:#87cefa");
        box.setStyle("-fx-background-color:#ffff55");
        anchorPane.setStyle("-fx-background-color:#ff7256");
//        box.setBackground(new Background(new BackgroundFill(Paint.valueOf("#550000"),new CornerRadii(20), new Insets(10))));
//        anchorPane.setBackground(new Background(new BackgroundFill(Paint.valueOf("#ff0000"),new CornerRadii(20), new Insets(10))));
        btn1.setOnAction(event -> {
            System.out.println("btn:setOnAction");
        });
        btn1.setOnMouseClicked(event -> {
            System.out.println("btn1：setOnMouseClicked");
        });
        btn1.addEventFilter(MouseEvent.MOUSE_CLICKED,event->{
            System.out.println("btn1:addEventFilter");

        });
        box.setOnMouseClicked(event -> {
            System.out.println("box：setOnMouseClicked");
        });
        box.addEventFilter(MouseEvent.MOUSE_CLICKED,event->{
            System.out.println("box:addEventFilter");
        });
        anchorPane.setOnMouseClicked(event -> {
            System.out.println("anchorPane:setOnMouseClicked");
        });
        anchorPane.addEventFilter(MouseEvent.MOUSE_CLICKED,event->{
            System.out.println("anchorPane:addEventFilter");
        });
        primaryStage.addEventFilter(MouseEvent.MOUSE_CLICKED,event -> {
            System.out.println("primaryStage:addEventFilter");
            event.consume();
        });
        primaryStage.addEventHandler(MouseEvent.MOUSE_CLICKED,event -> {
            System.out.println("primaryStage:addEventHandler");

        });
        FXUtil.setDefaultValue(primaryStage,anchorPane);
        box.setPrefWidth(anchorPane.getWidth()/2);
        box.setPrefHeight(anchorPane.getHeight()/2);
    }
}
