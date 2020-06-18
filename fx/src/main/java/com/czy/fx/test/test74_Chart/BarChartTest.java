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
 * 柱状图
 */
public class BarChartTest extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        var anchorPane = new AnchorPane();
        var btn = new Button("按钮");

        //x轴
        var x = new CategoryAxis();
        x.setLabel("指标");

        //x轴开始位置的边距
        x.setStartMargin(10);
        //x轴结束位置的边距
        x.setEndMargin(10);
        //开始结束是否保留边距
//        x.setGapStartAndEnd(false);
        //轴的方位
        x.setSide(Side.TOP);
        //x轴文本设置
//        x.setTickLabelFill();
//        x.setTickLabelFont();
        //文本与轴间距
        x.setTickLabelGap(10);
        //文本的角度
        x.setTickLabelRotation(45);
        //是否显示文本
        x.setTickLabelsVisible(true);
        //轴线长度
        x.setTickLength(10);
        //是否显示刻度
//        x.setTickMarkVisible(false);


        //y轴
        var y = new NumberAxis(0, 200, 10);
        y.setLabel("万亿");


        //
        var china = new XYChart.Series<>("中国", FXCollections.observableArrayList(
                new XYChart.Data<>("GDP", 100), new XYChart.Data<>("GMP", 56)));
        //
        var usa = new XYChart.Series<>("美国", FXCollections.observableArrayList(
                new XYChart.Data<>("GDP", 120), new XYChart.Data<>("GMP", 60)));

        var barChart = new BarChart(x, y, FXCollections.observableArrayList(china, usa));
        barChart.setTitle("世界前十国家2020生产总值");

        china.getData().forEach(data -> {
            var node = data.getNode();

            node.setOnMouseClicked(event -> {
                System.out.println("柱被点击了！");
            });
            //
            data.setExtraValue("额外信息");
        });

        //是否显示说明
        barChart.setLegendVisible(true);
        //说明显示方位
        barChart.setLegendSide(Side.RIGHT);
        //标题显示方位
        barChart.setTitleSide(Side.LEFT);
        //改变数据时，是否显示动画效果
        barChart.setAnimated(true);
        //默认是从左到右，这设值为从右到左。个人理解为阅读习惯的方向。
        barChart.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);

        //柱间距
        barChart.setBarGap(2);
        //组间距
        barChart.setCategoryGap(2);


        anchorPane.getChildren().addAll(btn, barChart);
        FXUtil.setDefaultValue(primaryStage, anchorPane);
        AnchorPane.setTopAnchor(barChart, btn.getHeight());


    }
}
