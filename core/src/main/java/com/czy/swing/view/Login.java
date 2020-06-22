package com.czy.swing.view;

import com.czy.core.dispatch.Dispatch;
import com.czy.util.json.JsonUtil;
import com.czy.util.model.StringMap;

import javax.swing.*;
import java.awt.*;

/**
 * @author chenzy
 *
 * @since 2020-04-29
 */
public class Login {
    public static void main(String[] args) {
//        SwingProject.getInstance().init();

        var frame = new MyFrame("图书管理系统", 500, 400, 300, 200, new GridLayout(3, 2));
        var userField = new JTextField();
        var psField = new JTextField();
        var loginButton = new MyButton("登录").addListener(e -> {
            var par = new StringMap<>();
            par.put("mobile",userField.getText());
            par.put("ps",psField.getText());
            Object result = Dispatch.getInstance().get("/login",par);
            System.out.println(result);
        });
        var resetButton = new MyButton("重置").addListener(e -> {
            userField.setText(null);
            psField.setText(null);
        });
        frame.add(new JLabel("手机号：")).add(userField)
                .add(new JLabel("密  码：")).add(psField)
                .add(loginButton).add(resetButton).setVisible(true);
    }
}
