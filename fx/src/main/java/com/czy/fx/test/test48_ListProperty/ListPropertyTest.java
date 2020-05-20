package com.czy.fx.test.test48_ListProperty;

import com.czy.fx.test.FXUtil;
import com.czy.user.model.User;
import com.czy.util.ObjectUtil;
import javafx.application.Application;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import static com.czy.util.StringUtil.println;

/**
 * @author chenzy
 * @description
 * @since 2020-05-20
 */
public class ListPropertyTest extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        var anchorPane = new AnchorPane();
        var  userList=ObjectUtil.getObjectList(User.class,"a","b","c");
        var userSimpleListProperty= new SimpleListProperty<>(FXCollections.observableArrayList(userList));
        userSimpleListProperty.addListener((observable, oldValue, newValue) -> {
            println("{}->{}",oldValue,newValue);
        });
        userSimpleListProperty.addListener((ListChangeListener<? super User>) observable -> {
            System.out.println(observable);

            while (observable.next()){
                System.out.println(observable.getAddedSubList());
                System.out.println(observable.wasAdded());
                /*是否排序:userList排序返回ture，但是userSimpleListProperty排序返回false（动作细分）*/
                System.out.println(observable.wasPermutated());
                /*排序操作后，getPermutation(旧索引)，得到新索引*/
                observable.getPermutation(0);

            }
        });
        var nameText = new TextField();
        var btn = new Button();
        btn.setOnAction(event -> {
            String name = nameText.getText();
            userSimpleListProperty.add(new User(name));
        });
        anchorPane.getChildren().addAll(nameText,btn);
        FXUtil.setDefaultValue(primaryStage,anchorPane);
        AnchorPane.setTopAnchor(btn,nameText.getHeight());
    }
}
