package com.czy.fx.test.test8_group;

import javafx.application.Application;
import javafx.collections.ListChangeListener;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

/**
 * @author chenzy
 * 
 * @since 2020/4/28
 */
public class GroupTest extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        Group group = new Group();
        Integer y = 10;
        {
            var btn = new Button();
            btn.setLayoutY(y+=20);
            group.getChildren().add(btn);
        }

        {
            var btn = new Button();
            btn.setLayoutY(y+=20);
            group.getChildren().add(btn);
        }
        {
            var btn = new Button();
            btn.setLayoutY(y+=20);
            group.getChildren().add(btn);
        }
        //监听组件改变
        group.getChildren().addListener((ListChangeListener<? super Node>) changeNode -> {
            System.out.println(changeNode);
        });
//        group.getChildren().toArray();//获取所有组件
//        group.contains(0,20);//判断坐标是否在组件上
//        group.getChildren().remove(0);//移除元素
//        group.getChildren().clear();//清除元素
//        group.setAutoSizeChildren(false);//不自动设置子元素的尺寸
        stage.setScene(new Scene(group));
        stage.show();
    }
}
