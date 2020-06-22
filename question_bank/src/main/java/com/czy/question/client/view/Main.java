package com.czy.question.client.view;

import com.czy.fx.util.FXMLUtil;
import com.czy.question.server.model.table.Subject;
import com.czy.util.ObjectUtil;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
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
        System.out.println(123);
    }

    @FXML private AnchorPane root;
    @FXML private MenuBar menuBar;
    @FXML private BorderPane borderPane;
    @FXML private TreeView leftRoot;
    @FXML private TreeItem questionRoot;
    @FXML private Button testButton;
    @FXML private void initialize() {
        borderPane.prefHeightProperty().bind(root.prefHeightProperty().subtract(menuBar.prefHeightProperty()));

        System.out.println(1213);

        var subjectList=ObjectUtil.createList(Subject.class,"语文","数学","发展心理学");
//        questionRoot.getChildren().addAll(subjectList);
//        leftRoot.setOnMouseClicked(event -> {
//            if (MouseButton.SECONDARY.equals(event.getButton())){
//                //右键
//
//            }
//
//        });
    }
}
