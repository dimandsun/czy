package com.czy.car.view.user;

import com.czy.car.model.User;
import com.czy.car.view.Q;
import com.czy.util.StringUtil;
import com.czy.util.model.StringMap;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
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

    }

    public void regist() {
        var mobile = mobileField.getText().trim();
        var pw = pwField.getText().trim();
        if (StringUtil.isBlank(mobile) || StringUtil.isBlank(pw)) {
            msgText.setText("帐号或者密码不能为空！");
            return;
        }
        Q.post("user", new StringMap<>("mobile", mobile).add("originalPS", pw)).addListener((observable, oldValue, newValue) -> {
            var data = newValue.getData();
            System.out.println(data);
        });


    }

}
