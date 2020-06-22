package com.czy.fx.test.test10_AnchorPane;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * @author chenzy
 *  定位窗格
 * @since 2020/4/28
 */
public class AnchorPaneTest extends Application {
    @Override
    public void start(Stage stage) {
        var btn = new Button("按钮1");
        var btn2 = new Button("按钮2");
        var btn3 = new Button("按钮3");
        var btn4 = new Button("按钮4");
        var anchorPane = new AnchorPane();
        Double pianYiLiang = 20.0;
        /*设置子控件的偏移量*/
        AnchorPane.setTopAnchor(btn, pianYiLiang);
        AnchorPane.setBottomAnchor(btn2, pianYiLiang);
        AnchorPane.setLeftAnchor(btn3, pianYiLiang);
        AnchorPane.setRightAnchor(btn4, pianYiLiang);
        {
            /*父组件不能管理此按钮，即不占用位置*/
            btn.setManaged(false);
            /*不可见*/
            btn.setVisible(true);
            /*透明。*/
            btn.setOpacity(1);
        /*这三者的视觉效果一致，但意义不一样:
        btn.setManaged(false)后，此控件还在,但其他控件会挤占此控件的位置
            且父控件anchorPane.getChildren().size()返回值不变，说明控件还在父控件中
        btn.setVisible(true)后，此控件还在，但其他控件不会挤占此控件的位置
            且父控件anchorPane.getChildren().size()返回值不变，说明控件还在父控件中
        btn.setOpacity(1)后，此控件还在，但其他控件不会挤占此控件的位置
            且父控件anchorPane.getChildren().size()返回值不变，说明控件还在父控件中
        */
        }
        /*样式*/
        anchorPane.setStyle("-fx-background-color: #aeeeee");
        /*内边距*/
        anchorPane.setPadding(new Insets(pianYiLiang));
        anchorPane.getChildren().addAll(btn, btn2, btn3, btn4);
        stage.setScene(new Scene(anchorPane));
        stage.show();
    }
}
