package com.czy.fx.test.test67_TreeTableView;

import com.czy.fx.test.FXUtil;
import com.czy.fx.User;
import javafx.application.Application;
import javafx.scene.control.Button;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * @author chenzy
 * @since 2020-06-09
 */
public class TreeTableViewTest extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        var anchorPane = new AnchorPane();
        var treeTableView = new TreeTableView<User>();

        treeTableView.setRoot(new TreeItem<>(new User("陈生")));
        var user1=treeTableView.getRoot().getValue();
        user1.setMobile("18720929479");
        user1.setAge(26);
        var user2=new User("张悦");
        user2.setAge(20);
        user2.setDelete(true);
        user2.setScore(0.9);
        treeTableView.getRoot().getChildren().addAll(new TreeItem<>(user2));
        var nameColumn = new TreeTableColumn<User, String>("姓名");
        var ageColumn = new TreeTableColumn<User, Integer>("年龄");
        var deleteColumn = new TreeTableColumn<User, Boolean>("是否删除");

        treeTableView.getColumns().addAll(nameColumn, ageColumn, deleteColumn);
        nameColumn.setCellValueFactory(new TreeItemPropertyValueFactory<>("name"));
        ageColumn.setCellValueFactory(new TreeItemPropertyValueFactory<>("age"));
        deleteColumn.setCellValueFactory(new TreeItemPropertyValueFactory<>("delete"));

        //宽度匹配内容
        nameColumn.setResizable(false);

        var btn = new Button();
        anchorPane.getChildren().addAll(btn, treeTableView);
        FXUtil.setDefaultValue(primaryStage, anchorPane);
        AnchorPane.setTopAnchor(treeTableView, btn.getHeight());
    }
}
