package com.czy.fx.test.test39_SplitPane;

import com.czy.fx.test.FXUtil;
import com.czy.util.ObjectUtil;
import com.czy.util.model.Par;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 * @author chenzy
 * @description
 * @since 2020/5/16
 */
public class SplitPaneTest extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setWidth(1000);
        primaryStage.setHeight(100);
        var anchorPane = new AnchorPane();
        var splitPane = new SplitPane();
        splitPane.setPrefWidth(primaryStage.getWidth());
        splitPane.setPrefHeight(primaryStage.getHeight());
        var btnList = FXUtil.getButtonList("1","2","3","4");

        var stackPaneList=ObjectUtil.getObjectList(StackPane.class,4);
        var i =new Par<Integer>(0);
        stackPaneList.forEach(stackPane -> {
            stackPane.getChildren().add(btnList.get(i.get()));
            i.set(i.get()+1);
        });

        btnList.forEach(button -> {
            button.setOnAction(event -> {
                System.out.println(button.getText()+":"+stackPaneList.get(Integer.valueOf(button.getText())-1).getChildren().size());
            });
        });
        splitPane.getItems().addAll(stackPaneList);
        /*给子控件分配区域*/
        splitPane.setDividerPosition(0,0.25);
        anchorPane.getChildren().addAll(splitPane);
        primaryStage.setScene(new Scene(anchorPane));
        splitPane.widthProperty().addListener((observable, oldValue, newValue) -> {
            stackPaneList.forEach(stackPane -> {
            });
        });
        primaryStage.widthProperty().addListener((observable, oldValue, newValue) -> {
            splitPane.setPrefWidth(newValue.doubleValue());
        });

        primaryStage.show();

    }
}
