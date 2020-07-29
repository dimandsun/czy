package test74_Chart;

import com.czy.fx.test.FXUtil;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.geometry.Side;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/**
 * @author chenzy
 * @since 2020-06-12
 * 饼状图
 */
public class PieChartTest extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        var anchorPane = new AnchorPane();
        var btn = new Button("按钮");
        var data1=new PieChart.Data("data1",10);
        var data2=new PieChart.Data("data2",40);
        var data3=new PieChart.Data("data2",20);

        var pieChart=new PieChart(FXCollections.observableArrayList(data1,data2,data3));
        //转角度
        pieChart.setStartAngle(45);
        //反方向旋转
        pieChart.setClockwise(true);
        //指示线是否显示
        pieChart.setLabelsVisible(false);
        //指示线的长度
        pieChart.setLabelLineLength(20);
        //是否显示说明
        pieChart.setLegendVisible(true);
        //说明显示方位
        pieChart.setLegendSide(Side.RIGHT);
        pieChart.setTitle("图表标题");
        //标题显示方位
        pieChart.setTitleSide(Side.LEFT);
        //改变数据时，是否显示动画效果
        pieChart.setAnimated(true);
        //数据块设值
        pieChart.getData().forEach(data -> {
            var node=data.getNode();
            var tooltip=new Tooltip(data.getName()+"-"+data.getPieValue());
            tooltip.setFont(Font.font(20));
            Tooltip.install(node,tooltip);
            data.pieValueProperty().addListener((observable, oldValue, newValue) -> {
                tooltip.setText(data.getName()+"-"+newValue);
            });
            node.setOnMouseClicked(event -> {
                System.out.println(data.getName()+"图片被点击");
            });
        });
        btn.setOnAction(event -> {
            data1.setPieValue(50);
        });


        anchorPane.getChildren().addAll(btn,pieChart);

        FXUtil.setDefaultValue(primaryStage,anchorPane);
        AnchorPane.setTopAnchor(pieChart,btn.getHeight());



    }
}
