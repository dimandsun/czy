package com.czy.car.view;

import com.czy.fx.util.FXMLUtil;
import javafx.application.Application;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 * @author chenzy
 * @since 2020-06-15
 */
public class Main extends Application {
    @Override
    public void start(Stage primaryStage) {


        GridPane gridPane=FXMLUtil.getRoot("car","com/czy/car/view/user/login.fxml");
        FXMLUtil.setDefault(primaryStage,gridPane);
        primaryStage.setTitle("共享汽车管理系统");
    }

    public static void main(String[] args) {

    }
}
