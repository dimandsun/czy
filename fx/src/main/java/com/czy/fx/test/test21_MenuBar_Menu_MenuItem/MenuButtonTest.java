package com.czy.fx.test.test21_MenuBar_Menu_MenuItem;

import com.czy.util.ObjectUtil;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * @author chenzy
 * @description
 * @since 2020/5/10
 */
public class MenuButtonTest extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        var ap=new AnchorPane();
        var menuButton=new MenuButton("测试");
        menuButton.getItems().addAll(ObjectUtil.getObjectList(MenuItem.class,"1","2"));
        ap.getChildren().add(menuButton);

        primaryStage.setScene(new Scene(ap));
        primaryStage.show();
    }
}
