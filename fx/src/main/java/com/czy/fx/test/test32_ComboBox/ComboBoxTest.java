package com.czy.fx.test.test32_ComboBox;

import com.czy.fx.User;
import com.czy.fx.test.FXUtil;
import com.czy.util.ObjectUtil;
import com.czy.util.text.StringUtil;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.StringConverter;

import java.util.List;
import java.util.function.Predicate;

/**
 * @author chenzy
 *
 * @since 2020-05-12
 */
public class ComboBoxTest extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        ComboBox comboBox = new ComboBox();
        var userList = ObjectUtil.createList(User.class, "czy", "陈志源", "张三");
        userList.get(0).setMobile("18720929479");
        var items = FXCollections.observableArrayList(userList);
        comboBox.setItems(items);
//        comboBox.getItems().addAll(userList);

        comboBox.setEditable(true);
        /*失去焦点时显示*/
        comboBox.setPromptText("请选择用户");
        /*列表为空时显示*/
        comboBox.setPlaceholder(new Text("没有查询到的用户"));

        comboBox.setConverter(new StringConverter<User>() {
            @Override
            public String toString(User user) {
                if (user == null) {
                    return null;
                }
                return user.getName();
            }

            /*编辑框输入文件后回车触发此方法*/
            @Override
            public User fromString(String s) {
                if (!comboBox.isShowing()) {
                    comboBox.show();
                }
                var user = getUser(userList, s);
                return user == null ? new User(s) : user;
            }
        });
        TextField textField = (TextField) comboBox.editorProperty().get();
        /*监听编辑框文本改变*/
        textField.textProperty().addListener((observableValue, oldValue, newValue) -> {
            if (!comboBox.isShowing()) {
                comboBox.show();
            }
            if (newValue==null){
                System.out.println("newValue:"+newValue);
            }
            StringUtil.println("编辑框内容改变,旧值：{},新值:{}", oldValue, newValue);
            var user = getUser(userList, newValue);
            if (user == null) {
                if (StringUtil.isNotBlank(comboBox.getValue())) {
                    comboBox.setValue(user);
                }
                if (StringUtil.isBlank(newValue)) {
                    if (!comboBox.getItems().equals(items)) {
                        comboBox.setItems(items);
                    }
                } else {
                    var list = comboBox.getItems().filtered((Predicate<User>) u -> u.getName().contains(newValue));
                    if (!list.isEmpty()) {
                        comboBox.setItems(list);
                    } else {
                        if (!comboBox.getItems().equals(items)) {
                            comboBox.setItems(items);
                        }
                    }
                }
            } else {
                if (StringUtil.isBlank(comboBox.getValue())) {
                    comboBox.setValue(user);
                }

            }
        });
        comboBox.getSelectionModel().selectedIndexProperty().addListener(observable -> {
//            System.out.println(observable);
        });
        comboBox.setCellFactory((Callback<ListView<User>, ListCell<User>>) listView -> {
            var listCell = new ListCell<User>() {
                @Override
                protected void updateItem(User user, boolean empty) {
                    super.updateItem(user, empty);
                    FXUtil.setColor(this, "#ff82ab");
                    if (!empty){
                        var box=new HBox(5);
                        var name = new Label(user.getName());
                        var mobile=new Label(user.getMobile());
                        box.getChildren().addAll(name,mobile);
                       this.setGraphic(box);
                    }
                }
            };
            return listCell;
        });

        var ap = new AnchorPane();
        var btn = new Button();

        btn.setOnAction(event -> {

        });
        ap.getChildren().addAll(comboBox, btn);
        AnchorPane.setBottomAnchor(btn, 10d);
        stage.setScene(new Scene(ap));
        stage.setHeight(800);
        stage.show();
    }

    static User getUser(List<User> userList, String name) {
        if (StringUtil.isBlank(name)) {
            return null;
        }
        for (var user : userList) {
            if (user.getName().equals(name)) {
                return user;
            }
        }
        return null;
    }
}
