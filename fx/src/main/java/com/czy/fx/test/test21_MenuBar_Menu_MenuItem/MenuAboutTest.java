package com.czy.fx.test.test21_MenuBar_Menu_MenuItem;

import com.czy.fx.test.FXUtil;
import com.czy.util.StringUtil;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.Mnemonic;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * @author chenzy
 * @description
 * @since 2020/5/10
 */
public class MenuAboutTest extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        var ap = new AnchorPane();

        var menuBar = new MenuBar();
        Integer i = 1;
        menuBar.getMenus().add(new Menu("菜单" + (i++)));
        menuBar.getMenus().add(new Menu("菜单" + (i++)));
        menuBar.getMenus().add(new Menu("菜单" + (i++)));
        menuBar.getMenus().get(0).getItems().addAll(FXUtil.getObjectList(MenuItem.class,"1","a"));
        /*菜单渲染时触发*/
        menuBar.getMenus().get(0).setOnShowing(event -> System.out.println("setOnShowing"));
        /*菜单渲染完时触发*/
        menuBar.getMenus().get(0).setOnShown(event -> System.out.println("setOnShown"));

        /*菜单项加图标*/
        var mi = new MenuItem("带图标的菜单项");
        mi.setGraphic(new ImageView("qq.jpg"));
        menuBar.getMenus().get(0).getItems().add(mi);
        mi.setOnAction(event -> System.out.println("你好啊！"));

        /*菜单项加快捷键*/
        mi.setAccelerator(new KeyCodeCombination(KeyCode.K));

        /*菜单项被点击*/
        menuBar.getMenus().get(0).getItems().get(0).setOnAction(event -> System.out.println("菜单项被点击"));
        /*menuBar的宽度不会随着父控件的宽度改变而改变，需要设置监听事件*/
        primaryStage.widthProperty().addListener((observableValue, oldNumber, newNumber) -> {
            StringUtil.println("窗口宽度{}->{}", oldNumber, newNumber);
            menuBar.setPrefWidth(newNumber.doubleValue());
        });
        ap.getChildren().add(menuBar);
        primaryStage.setScene(new Scene(ap));
        primaryStage.show();
    }
}
