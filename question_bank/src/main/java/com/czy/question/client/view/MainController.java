package com.czy.question.client.view;

import com.czy.question.server.model.table.Subject;
import com.czy.util.ObjectUtil;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.TextFieldTreeCell;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.util.StringConverter;

/**
 * @author chenzy
 * @since 2020/6/23
 * 若Application类作为控制器，JavaFX将创建应用程序类的新实例，并将新实例用作控制器。即会存在两个Application实例，两个实例中属性内容不同，会出现问题
 *
 */
public class MainController {
    @FXML private AnchorPane root;
    @FXML private MenuBar menuBar;
    @FXML private BorderPane borderPane;
    @FXML private TreeView<Subject> leftRoot;
    @FXML private TreeItem<Subject> questionRoot;
    @FXML private Button testButton;
    @FXML private void initialize() {
        var subjectList= ObjectUtil.createList(Subject.class,"语文","数学","发展心理学");
        subjectList.forEach(subject -> {
            questionRoot.getChildren().add(new TreeItem<>(subject));
        });

        leftRoot.setCellFactory(TextFieldTreeCell.forTreeView(new StringConverter<Subject>() {
            @Override
            public String toString(Subject object) {
                if (object==null){
                    return "1";
                }
                return object.getName();
            }

            @Override
            public Subject fromString(String string) {
                return new Subject("123");
            }
        }));
        leftRoot.setOnMouseClicked(event -> {
            if (MouseButton.SECONDARY.equals(event.getButton())){
                //右键
            }

        });
    }
}
