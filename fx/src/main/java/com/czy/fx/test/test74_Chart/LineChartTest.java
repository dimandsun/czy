package com.czy.fx.test.test74_Chart;

import com.czy.fx.test.FXUtil;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.geometry.NodeOrientation;
import javafx.geometry.Side;
import javafx.scene.chart.*;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * @author chenzy
 * @since 2020-06-12
 */
public class LineChartTest extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        var anchorPane = new AnchorPane();
        var btn = new Button("按钮");
        var x = new NumberAxis("x轴", 0, 100, 10);
        var y = new NumberAxis("y轴", 0, 100, 10);


        var xy1 = new XYChart.Series<Number,Number>(FXCollections.observableArrayList(new XYChart.Data<>(10, 10)
                , new XYChart.Data<>(10, 20), new XYChart.Data<>(50, 56)));
        xy1.setName("xy1");
        var xy2 = new XYChart.Series<Number,Number>(FXCollections.observableArrayList(new XYChart.Data<>(9, 10)
                , new XYChart.Data<>(25, 20), new XYChart.Data<>(23, 56)));
        xy1.setName("xy2");


        var lineChart = new LineChart<>(x, y, FXCollections.observableArrayList(xy1, xy2));


        anchorPane.getChildren().addAll(btn, lineChart);
        FXUtil.setDefaultValue(primaryStage, anchorPane);
        AnchorPane.setTopAnchor(lineChart, btn.getHeight());
    }
}
