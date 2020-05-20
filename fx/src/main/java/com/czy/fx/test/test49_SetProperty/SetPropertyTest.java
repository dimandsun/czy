package com.czy.fx.test.test49_SetProperty;

import com.czy.fx.test.FXUtil;
import com.czy.user.model.User;
import com.czy.util.ListUtil;
import com.czy.util.ObjectUtil;
import javafx.application.Application;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleSetProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableSet;
import javafx.collections.SetChangeListener;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.util.Arrays;
import java.util.List;

import static com.czy.util.StringUtil.println;

/**
 * @author chenzy
 * @since 2020-05-20
 * @description
 */
public class SetPropertyTest extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        var anchorPane = new AnchorPane();
        var userList= ObjectUtil.getObjectList(User.class,"a","b","c");
        var setProperty= new SimpleSetProperty<>( FXCollections.observableSet(ListUtil.list2Array(userList)));
        setProperty.addListener((observable, oldValue, newValue) -> {
            println("{}->{}",oldValue,newValue);
        });
        setProperty.addListener((SetChangeListener<? super User>) observable -> {
            System.out.println(observable);
        });
        var nameText = new TextField();
        var btn = new Button();
        btn.setOnAction(event -> {
            String name = nameText.getText();
            setProperty.add(new User(name));
        });
        anchorPane.getChildren().addAll(nameText,btn);
        FXUtil.setDefaultValue(primaryStage,anchorPane);
        AnchorPane.setTopAnchor(btn,nameText.getHeight());
    }
}
