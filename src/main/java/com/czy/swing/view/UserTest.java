package com.czy.swing.view;

import com.czy.swing.entrance.Dispatch;
import com.czy.user.UserProject;
import com.czy.util.json.JsonUtil;

import javax.swing.*;
import java.awt.*;

/**
 * @author chenzy
 * @description
 * @since 2020-04-23
 */
public class UserTest {
    public static void main(String[] args) {
        UserProject.getInstance().init();
        var frame = new MyFrame("图书管理系统", 500, 400, 300, 200, new GridLayout(3, 2));
        var userField = new JTextField();
        var psField = new JTextField();
        var submitButton = new MyButton("提交").addListener(e -> {
            var par = JsonUtil.createJsonNode();
            par.put("name",userField.getText());
            par.put("ps",psField.getText());
            Object result =Dispatch.getInstance().post("/user",par);
            System.out.println(result);
        });
        var resetButton = new MyButton("重置").addListener(e -> {
            userField.setText(null);
            psField.setText(null);
        });
        frame.add(new JLabel("用户名：")).add(userField)
                .add(new JLabel("密  码：")).add(psField)
                .add(submitButton).add(resetButton).setVisible(true);
//        容器 组件 布局管理器
    }
}
