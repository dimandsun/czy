package com.czy.fx.test.test74_Chart;

import com.czy.fx.test.FXUtil;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.scene.chart.*;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * @author chenzy
 * @since 2020-06-12
 * 气泡图
 */
public class BubbleChartTest extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        var anchorPane = new AnchorPane();
        var btn = new Button("按钮");
        //X轴是字符串
        var xt = new CategoryAxis();
        //x轴是数字
        var x = new NumberAxis("x轴", 0, 100, 10);
        var y = new NumberAxis("y轴", 0, 100, 10);

        var xy1 = new XYChart.Series<Number,Number>(FXCollections.observableArrayList(new XYChart.Data<>(10, 10)
                , new XYChart.Data<>(10, 20), new XYChart.Data<>(50, 56)));
        xy1.setName("xy1");
        var xy2 = new XYChart.Series<Number,Number>(FXCollections.observableArrayList(new XYChart.Data<>(9, 10)
                , new XYChart.Data<>(25, 20), new XYChart.Data<>(23, 56)));
        xy2.setName("xy2");


        var bubbleChart = new BubbleChart<>(x, y, FXCollections.observableArrayList(xy1, xy2));
        xy1.getData().forEach(data->{
            data.setExtraValue(10);//气泡半径
//            data.setNode(new HBox());
            Tooltip.install(data.getNode(),new Tooltip("xy1:"+data.getXValue()+"-"+data.getYValue()));
            data.getNode().setOnMouseClicked(event -> {

            });
        });
        xy2.getData().forEach(data->{
//            data.setNode(new HBox());
            Tooltip.install(data.getNode(),new Tooltip("xy2:"+data.getXValue()+"-"+data.getYValue()));
        });


        anchorPane.getChildren().addAll(btn, bubbleChart);
        FXUtil.setDefaultValue(primaryStage, anchorPane);
        AnchorPane.setTopAnchor(bubbleChart, btn.getHeight());
    }
}
