package com.czy.car_client.view.user;

import com.czy.car_client.view.Q;
import com.czy.util.text.StringUtil;
import com.czy.util.model.StringMap;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;

/**
 * @author chenzy
 * @since 2020-06-15
 */
public class LoginController {
    @FXML
    private TextField mobileField;
    @FXML
    private PasswordField pwField;
    @FXML
    private Text msgText;

    @FXML
    private void initialize() {

    }


    public void login() {
        var mobile = mobileField.getText().strip();
        var pw = pwField.getText().strip();
        if (!verify(mobile, pw)) {
            return;
        }
        Q.get("user", new StringMap<>("mobile", mobile).add("original_ps", pw)).addListener((observable, oldValue, newValue) -> {
            var data = newValue.getData();
            if (data == null) {
                msgText.setText("系统异常，请联系管理员");
                return;
            }
            msgText.setText(data.getMessage());
            if (data.isNormal()) {
                msgText.setFill(Paint.valueOf("lime"));
            }
            System.out.println(data);
        });

    }

    public void regist() {
        var mobile = mobileField.getText().strip();
        var pw = pwField.getText().strip();
        if (!verify(mobile, pw)) {
            return;
        }
        Q.post("user", new StringMap<>("mobile", mobile).add("original_ps", pw)).addListener((observable, oldValue, newValue) -> {
            var data = newValue.getData();
            if (data == null) {
                msgText.setText("系统异常，请联系管理员");
                return;
            }
            msgText.setText(data.getMessage());
            if (data.isNormal()) {
                msgText.setFill(Paint.valueOf("lime"));
            }
            System.out.println(data);
        });
    }

    private Boolean verify(String mobile, String pw) {
        if (StringUtil.isBlank(mobile) || StringUtil.isBlank(pw)) {
            msgText.setText("帐号或者密码不能为空！");
            return false;
        }
        return true;
    }
}
