package com.czy.fx.a;

import javafx.application.Application;
import javafx.beans.InvalidationListener;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.util.Map;

/**
 * @author chenzy
 * @description
 * @since 2020/5/10
 */
public class Loging extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        var gp=new GridPane();

        var codeText=new TextField();
        var psText=new PasswordField();
        var loginBtn = new Button("登录");
        var registerBtn = new Button("注册");
        gp.add(new Label("帐号："),0,0);
        gp.add(codeText,1,0);
        gp.add(new Label("帐号："),0,1);
        gp.add(psText,1,1);

        gp.add(loginBtn,0,2);
        gp.add(registerBtn,1,2);

        /*绑定数据：简化：codeText.getProperties().put("","")*/
        codeText.setUserData("用户：陈志源");
       /* codeText.getProperties().addListener((MapChangeListener<? super Object, ? super Object>) change->{
            System.out.println(change);
        });*/
        codeText.getProperties().addListener((InvalidationListener) change -> System.out.println(change));
        loginBtn.setOnAction(event -> {
            codeText.setUserData("用户：陈志源123");
            Map a=codeText.getProperties();
            System.out.println(a);
        });
        primaryStage.setScene(new Scene(gp));
        primaryStage.show();
    }
}
