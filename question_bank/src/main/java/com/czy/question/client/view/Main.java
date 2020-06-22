package com.czy.question.client.view;

import com.czy.fx.util.FXMLUtil;
import com.czy.question.server.model.table.Subject;
import com.czy.util.ObjectUtil;
import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.beans.property.Property;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.util.Collection;

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
