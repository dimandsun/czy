package com.czy.question.client.view;

import com.czy.fx.util.FXMLUtil;
import javafx.application.Application;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * @author chenzy
 * @date 2020-06-22
 */
public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        AnchorPane root = FXMLUtil.getRoot("question_bank", "com/czy/question/client/view/main.fxml");
        FXMLUtil.setDefault(primaryStage, root);
        primaryStage.setTitle("我的题库");
        MenuBar menuBar = (MenuBar) root.lookup("#menuBar");
        BorderPane borderPane = (BorderPane) root.lookup("#borderPane");
        AnchorPane.setTopAnchor(borderPane,menuBar.getHeight());
    }

}
