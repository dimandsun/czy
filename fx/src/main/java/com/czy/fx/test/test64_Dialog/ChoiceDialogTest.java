package com.czy.fx.test.test64_Dialog;

import com.czy.fx.test.FXUtil;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * @author chenzy
 * @since 2020-06-05
 */
public class ChoiceDialogTest extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        var anchorPane = new AnchorPane();
        var btn = new Button();
        btn.setOnAction(event -> {
            var observableList=FXCollections.observableArrayList("a","b","c");

            var choiceDialog=new ChoiceDialog<>("b",observableList);
            choiceDialog.selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                System.out.println("选择改变时调用！"+newValue);
            });
            choiceDialog.show();
        });
        anchorPane.getChildren().addAll(btn);
        FXUtil.setDefaultValue(primaryStage,anchorPane);
    }
}
