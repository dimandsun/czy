package com.czy.car.view;

import com.czy.core.LogInfo;
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
        //加载日志框架
        var projectInfo = new ProjectInfo("car");
        LogInfo.initLog(projectInfo);
        //初始化框架
        var projectContainer = ProjectContainer.getInstance();
        projectContainer.addProjectInfo(projectInfo).initProject();
        //设置默认的数据源
        projectContainer.setDefaultDataSourceKey(projectInfo);
        //渲染页面
        Main.launch(args);
    }
}
