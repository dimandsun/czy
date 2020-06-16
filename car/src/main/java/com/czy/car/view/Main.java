package com.czy.car.view;

import com.czy.core.ProjectContainer;
import com.czy.core.model.ProjectInfo;
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


        GridPane gridPane = FXMLUtil.getRoot("car", "com/czy/car/view/user/login.fxml");
        FXMLUtil.setDefault(primaryStage, gridPane);
        primaryStage.setTitle("共享汽车管理系统");
    }

    public static void main(String[] args) {
        //初始化框架
        var projectContainer=ProjectContainer.getInstance();
        projectContainer.addProjectInfo(new ProjectInfo("car")).initProject();
        projectContainer.getBeanMap();
        //渲染页面
        Main.launch(args);
    }
}
