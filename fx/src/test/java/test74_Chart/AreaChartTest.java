package test74_Chart;

import com.czy.fx.test.FXUtil;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * @author chenzy
 * @since 2020-06-12
 * 区域图
 */
public class AreaChartTest extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        var anchorPane = new AnchorPane();
        var btn = new Button("按钮");
        //X轴是字符串
        var x = new CategoryAxis();

        var y = new NumberAxis("y轴", 0, 100, 10);

        var xy1 = new XYChart.Series<String,Number>(FXCollections.observableArrayList(new XYChart.Data<>("a", 10)
                , new XYChart.Data<>("b", 20), new XYChart.Data<>("c", 56)));
        xy1.setName("xy1");
        var xy2 = new XYChart.Series<String,Number>(FXCollections.observableArrayList(new XYChart.Data<>("a", 15)
                , new XYChart.Data<>("b", 12), new XYChart.Data<>("c", 42)));
        xy2.setName("xy2");


        var scatterChart = new AreaChart<>(x, y, FXCollections.observableArrayList(xy1, xy2));
        xy1.getData().forEach(data->{
//            data.setNode(new HBox());
            Tooltip.install(data.getNode(),new Tooltip("xy1:"+data.getXValue()+"-"+data.getYValue()));
            data.getNode().setOnMouseClicked(event -> {

            });
        });
        xy2.getData().forEach(data->{
//            data.setNode(new HBox());
            Tooltip.install(data.getNode(),new Tooltip("xy2:"+data.getXValue()+"-"+data.getYValue()));
        });

        //不显示点
        scatterChart.setCreateSymbols(false);
        anchorPane.getChildren().addAll(btn, scatterChart);
        FXUtil.setDefaultValue(primaryStage, anchorPane);
        AnchorPane.setTopAnchor(scatterChart, btn.getHeight());
    }
}
