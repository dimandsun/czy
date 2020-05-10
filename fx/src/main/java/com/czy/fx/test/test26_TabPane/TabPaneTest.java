package com.czy.fx.test.test26_TabPane;

import com.czy.fx.test.FXUtil;
import javafx.application.Application;
import javafx.geometry.Side;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * @author chenzy
 * @description
 * @since 2020/5/10
 */
public class TabPaneTest extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        var tp = new TabPane();
        tp.getTabs().addAll(FXUtil.getObjectList(Tab.class,"1","2","3"));
        var tab = new Tab("页签");
        var box=new VBox();
        box.getChildren().addAll(FXUtil.getButtonList("a","b","b","b","b","b","b","b"));
        tab.setContent(box);
        tp.getTabs().add(tab);
        tab.setGraphic(new ImageView("qq.jpg"));
        tab.setClosable(false);//设置页签不可关闭

        /*页签朝向*/
//        tp.setSide(Side.LEFT);
        /*设置页签的图片不随页签朝向改变而改变*/
        tp.setRotateGraphic(false);
        /*设置默认选中的页签*/
        tp.getSelectionModel().select(tab);
        /*允许关闭所有页签:这是默认值，可以设置为都不可以关闭*/
        tp.setTabClosingPolicy(TabPane.TabClosingPolicy.ALL_TABS);

        /*切换页签时触发*/
        tp.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) ->{
            System.out.println(newValue);
        } );
        /*指定页签选中状态改变时触发，选中触发一次，选其他页签时触发一次*/
        tab.setOnSelectionChanged(event -> System.out.println(event));

        /**/
        tp.setOnMouseClicked(event -> System.out.println(event));
        /*event.consume()终止事件传递*/
        tab.setOnClosed(event -> {event.consume();});
        tab.setOnCloseRequest(event -> System.out.println(event));
        var ap=new AnchorPane();
        ap.getChildren().addAll(tp);
        primaryStage.setScene(new Scene(ap));
        primaryStage.show();
    }
}
