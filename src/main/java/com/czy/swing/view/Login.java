package com.czy.swing.view;

import com.czy.swing.entrance.Dispatch;
import com.czy.swing.entrance.SwingProject;
import com.czy.util.json.JsonUtil;

import javax.swing.*;
import java.awt.*;

/**
 * @author chenzy
 * @description
 * @since 2020-04-23
 */
public class Login {
    public static void main(String[] args) {
        SwingProject.getInstance().init();
        var frame = new MyFrame("图书管理系统", 500, 400, 300, 200, new GridLayout(3, 2));
        var userField = new JTextField();
        var psField = new JTextField();
        var loginButton = new MyButton("登录").addListener(e -> {
            var par = JsonUtil.createJsonNode();
            par.put("userName",userField.getText());
            par.put("userPS",psField.getText());
            Object result =Dispatch.getInstance().get("/login",par);
            System.out.println(result);
        });
        var resetButton = new MyButton("重置").addListener(e -> {
            userField.setText(null);
            psField.setText(null);
        });
        frame.add(new JLabel("用户名：")).add(userField)
                .add(new JLabel("密  码：")).add(psField)
                .add(loginButton).add(resetButton).setVisible(true);
//        容器 组件 布局管理器
    }
}
