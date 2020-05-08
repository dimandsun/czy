package com.czy.fx.test.test9_button;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.stage.Stage;


/**
 * @author chenzy
 * @description
 * @since 2020/4/28
 */
public class ButtonTest extends Application {
    @Override
    public void start(Stage stage) throws Exception {

        var btn = new Button("按钮");
        btn.setFont(Font.font("sans-serif", 40));

        //#004455 颜色加两位表示透明度
        btn.setBackground(new Background(new BackgroundFill(Paint.valueOf("#004455"), new CornerRadii(20), new Insets(10))));
        btn.setBorder(new Border(new BorderStroke(Paint.valueOf("#8fbc8f"), BorderStrokeStyle.DASHED, new CornerRadii(20), new BorderWidths(5))));
//       btn.setStyle(""); css设置样式

        btn.addEventHandler(MouseEvent.ANY, mouseEvent -> {
//            MouseButton.
            System.out.println(mouseEvent);
        });
        btn.setOnKeyPressed(keyEvent -> {
            if (KeyCode.UNDEFINED.equals(keyEvent.getCode())) {
                btn.setText("请切换为英文输入法");
            } else {
                btn.setText(keyEvent.getCode().getName());
            }

        });

        //

        var group = new Group();
        group.getChildren().add(btn);
        var scene = new Scene(group);
        //添加快捷键
        scene.addMnemonic(new Mnemonic(btn, new KeyCodeCombination(KeyCode.C, KeyCombination.ALT_DOWN)));
        btn.setOnAction(actionEvent -> {
            System.out.println("hehhee");
        });
        //添加快捷键 SHORTCUT_DOWN在window是ctr 苹果是另一个
        scene.getAccelerators().put(new KeyCodeCombination(KeyCode.D,KeyCombination.SHORTCUT_DOWN),()->{
            System.out.println(124);
        });
        stage.setScene(scene);
        stage.show();

    }
}
