package test21_MenuBar_Menu_MenuItem;

import com.czy.fx.test.FXUtil;
import com.czy.util.ObjectUtil;
import com.czy.util.text.StringUtil;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.List;

/**
 * @author chenzy
 * @since 2020/5/10
 *  menu不是node
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
        menuBar.getMenus().add(new Menu("菜单" + (i++)));
        menuBar.getMenus().add(new Menu("菜单" + (i++)));
        menuBar.getMenus().get(0).getItems().addAll(ObjectUtil.createList(MenuItem.class,"1","a"));
        /*菜单渲染时触发*/
        menuBar.getMenus().get(0).setOnShowing(event -> System.out.println("setOnShowing"));
        /*菜单渲染完时触发*/
        menuBar.getMenus().get(0).setOnShown(event -> System.out.println("setOnShown"));

        /*菜单项分隔栏*/
        var separ=new SeparatorMenuItem();
        /*菜单项加图标*/
        var mi = new MenuItem("带图标的菜单项");
        mi.setGraphic(new ImageView("qq.jpg"));
        menuBar.getMenus().get(0).getItems().addAll(separ,mi);
        mi.setOnAction(event -> System.out.println("你好啊！"));
//        mi.setDisable(true);//禁用
//        mi.setVisible(true);//不可见
        /*菜单项加快捷键*/
        mi.setAccelerator(new KeyCodeCombination(KeyCode.K));

        /*菜单项被点击*/
        menuBar.getMenus().get(0).getItems().get(0).setOnAction(event -> System.out.println("菜单项被点击"));
        /*menuBar的宽度不会随着父控件的宽度改变而改变，需要设置监听事件*/
        primaryStage.widthProperty().addListener((observableValue, oldNumber, newNumber) -> {
            StringUtil.println("窗口宽度{}->{}", oldNumber, newNumber);
            menuBar.setPrefWidth(newNumber.doubleValue());
        });

        /*menu作为菜单项*/
        var m=new Menu("子菜单");
        m.getItems().addAll(ObjectUtil.createList(MenuItem.class,"嘿嘿","哈哈"));
        menuBar.getMenus().get(1).getItems().add(m);
        /*单选菜单项*/
        List<RadioMenuItem> radioMenuItems = ObjectUtil.createList(RadioMenuItem.class,"单选项1","单选项2");
        var tg=new ToggleGroup();
//        ClassUtil.exexList(radioMenuItems,"setToggleGroup",tg);
        tg.getToggles().addAll(radioMenuItems);
        menuBar.getMenus().get(1).getItems().addAll(radioMenuItems);
        radioMenuItems.get(0).setSelected(true);//默认被选中
        radioMenuItems.get(0).isSelected();//单选项是否被选中
        /*监听单选项点击事件*/
        tg.selectedToggleProperty().addListener(c->{
            System.out.println(c);
        });

        /*复选菜单项*/
        List<CheckMenuItem> checkMenuItemList = ObjectUtil.createList(CheckMenuItem.class,"复选项1","复选项2");
        menuBar.getMenus().get(2).getItems().addAll(checkMenuItemList);

        /*自定义菜单项*/
        var customMenuItem=new CustomMenuItem();
        customMenuItem.setContent(new Button("自定义菜单项"));
        customMenuItem.setContent(new ProgressBar(0.3));//进度条
        menuBar.getMenus().get(2).getItems().addAll(new SeparatorMenuItem(),customMenuItem);
        var customMenuItem2=new CustomMenuItem();
        var box=new VBox();
        box.getChildren().addAll(FXUtil.getButtonList("a","a"));
        customMenuItem2.setContent(box);
        menuBar.getMenus().get(2).getItems().addAll(customMenuItem2);


        var menuButton=new MenuButton("menuButton");
        menuButton.getItems().addAll(ObjectUtil.createList(MenuItem.class,"1","2"));
        ap.getChildren().add(menuButton);
        AnchorPane.setBottomAnchor(menuButton,10d);


        var splitMenuButton=new SplitMenuButton();
        splitMenuButton.setText("splitMenuButton");
        splitMenuButton.getItems().addAll(ObjectUtil.createList(MenuItem.class,"1","2"));
        ap.getChildren().add(splitMenuButton);
        AnchorPane.setBottomAnchor(splitMenuButton,50d);


        var contextMenu=new ContextMenu();
        contextMenu.getItems().addAll(ObjectUtil.createList(MenuItem.class,"a"));
        var btn = new Button("右键弹出菜单");
        btn.setContextMenu(contextMenu);
        btn.setOnContextMenuRequested(event -> {
            System.out.println(event);
        });
        ap.getChildren().add(btn);
        AnchorPane.setBottomAnchor(btn,70d);

        ap.getChildren().add(menuBar);

        ap.setPrefWidth(500);
        ap.setPrefHeight(500);
        primaryStage.setScene(new Scene(ap));
        primaryStage.show();
    }

}
