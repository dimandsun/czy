package com.czy.fx.test.test40_Spinner;

import com.czy.user.model.User;
import com.czy.util.ListUtil;
import com.czy.util.ObjectUtil;
import com.czy.util.StringUtil;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.event.EventType;
import javafx.scene.Scene;
import javafx.scene.control.Spinner;
import javafx.scene.control.skin.SpinnerSkin;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.StringConverter;

/**
 * @author chenzy
 * @description
 * @since 2020/5/16
 */
public class SpinnerTest extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setWidth(1000);
        primaryStage.setHeight(200);
        var anchorPane = new AnchorPane();

        var userList = ObjectUtil.getObjectList(User.class, "陈晓云", "陈志源", "czy");
        var userObservableList = FXCollections.observableArrayList(userList);
        var userSpinner = new Spinner<>(userObservableList);
        userSpinner.setEditable(true);
        var userSpinnerValueFactory = userSpinner.getValueFactory();
        userSpinnerValueFactory.valueProperty().addListener((observable, oldValue, newValue) -> {
            System.out.println("选项改变监听：" + newValue);
        });
        userSpinnerValueFactory.setConverter(new StringConverter<>() {
            @Override
            public String toString(User user) {
                return user == null ? "" : user.getName();
            }

            @Override
            public User fromString(String string) {
                if (StringUtil.isBlank(string)) {
                    return null;
                }
                for (var user : userObservableList) {
                    if (string.equals(user.getName()) || string.equals(user.toString())) {
                        return user;
                    }
                }
                return null;
            }
        });
//        userSpinnerValueFactory.setValue(userList.get(0));
//        userSpinner.getStyleClass().add()
        userSpinner.editorProperty().addListener((observable, oldValue, newValue) -> {
//            System.out.println("editor:"+newValue);
        });
        userSpinner.valueProperty().addListener((observable, oldValue, newValue) -> {
//            System.out.println(newValue);
        });
        anchorPane.getChildren().add(userSpinner);
        primaryStage.setScene(new Scene(anchorPane));
        primaryStage.show();

        SpinnerSkin skin= (SpinnerSkin) userSpinner.getSkin();
        /*向上箭头*/
        var upArrow= (StackPane) skin.getChildren().get(1);
        /*向下箭头*/
        var downArrow= (StackPane) skin.getChildren().get(2);
//        User curUser=new User();
//        upArrow.removeEventHandler(EventType.On);
//        upArrow.getEventDispatcher().ge
        upArrow.setOnMouseClicked(event -> {
            if (ListUtil.isEmpty(userList)){
                return;
            }
            if (userList.get(userList.size()-1).equals(userSpinner.getValue())){
                userSpinner.decrement(userList.size());
            }
//            event.consume();
        });
        downArrow.setOnMouseClicked(event -> {
            if (ListUtil.isEmpty(userList)){
                return;
            }
            if (userList.size()>0&&userList.get(0).equals(userSpinner.getValue())){
                userSpinner.increment(userList.size());
            }
//            event.consume();
        });
    }
}
