package com.czy.fx.test.test13_FlowPane;

import com.czy.fx.test.FXUtil;
import javafx.application.Application;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

/**
 * @author chenzy
 * @since 2020/5/8
 *  流式布局
 */
public class FlowPaneTest extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        var flowPane=new FlowPane();
        flowPane.getChildren().addAll(FXUtil.getButtonList("","",""));
        /*设置子组件水平间距*/
        flowPane.setHgap(10);
        /*设置子组件垂直间距*/
        flowPane.setVgap(10);
        /*布局方向*/
        flowPane.setOrientation(Orientation.HORIZONTAL);
        primaryStage.setScene(new Scene(flowPane));
        primaryStage.show();
    }
}
