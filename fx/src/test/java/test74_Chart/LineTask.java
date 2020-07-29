package test74_Chart;

import com.czy.fx.test.FXUtil;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.concurrent.ScheduledService;
import javafx.concurrent.Task;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.util.StringConverter;

import java.util.ArrayList;
import java.util.Random;

/**
 * @author chenzy
 * @since 2020-06-15
 */
public class LineTask extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        var anchorPane = new AnchorPane();
        var btn = new Button("播放");
        var btn2 = new Button("暂停");
        var x = new NumberAxis("x轴", 0, 20, 1);

        var y = new NumberAxis("y轴", 0, 100, 10);


        var line1 = new XYChart.Series<Number, Number>();
        line1.setName("折线1");
        var line2 = new XYChart.Series<Number, Number>();
        line2.setName("折线2");
        var lineChart = new LineChart<>(x, y, FXCollections.observableArrayList(line1, line2));

//        lineChart.setAnimated(false);//去掉动画
        var task = new DataTask();
        task.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == null) {
                return;
            }
            var index = line1.getData().size();
            if (index > 18) {
                x.setLowerBound(x.getLowerBound() + 1);
                x.setUpperBound(x.getUpperBound() + 1);
            }
            if (index == 25) {
                line1.getData().clear();
                line2.getData().clear();
                index=0;
                x.setLowerBound(0);
                x.setUpperBound(20);
            }
            line1.getData().add(new XYChart.Data<>(index, newValue.get(0)));
            line2.getData().add(new XYChart.Data<>(index, newValue.get(1)));

        });
        task.setDelay(Duration.seconds(0));
        task.setPeriod(Duration.seconds(0.5));
        btn.setOnAction(event -> {
            if (!task.isRunning()) {
                task.start();
            }
        });
        btn2.setOnAction(event -> {
            task.cancel();
            task.reset();
        });


        //显示网格横线
        lineChart.setHorizontalGridLinesVisible(true);
        //显示网格纵线
        lineChart.setVerticalGridLinesVisible(true);
        //显示水平0点线
        lineChart.setHorizontalZeroLineVisible(true);
        //显示垂直水平0点线
        lineChart.setVerticalZeroLineVisible(true);

        //网格列背景
        lineChart.setAlternativeColumnFillVisible(true);
        //网格行背景
        lineChart.setAlternativeRowFillVisible(true);

        //定制纵轴坐标值
        y.setTickLabelFormatter(new StringConverter<Number>() {
            @Override
            public String toString(Number object) {
                return object.toString()+"%";
            }

            @Override
            public Number fromString(String string) {
                return null;
            }
        });

        anchorPane.getChildren().addAll(btn, btn2, lineChart);
        FXUtil.setDefaultValue(primaryStage, anchorPane);
        AnchorPane.setLeftAnchor(btn2, btn.getWidth() + 10);
        AnchorPane.setTopAnchor(lineChart, btn.getHeight());
    }

    class DataTask extends ScheduledService<ArrayList<Integer>> {

        @Override
        protected Task<ArrayList<Integer>> createTask() {
            return new Task<ArrayList<Integer>>() {
                @Override
                protected ArrayList<Integer> call() {
                    var random = new Random();
                    int value1 = random.nextInt(100);
                    int value2 = random.nextInt(100);
                    var data = new ArrayList<Integer>();
                    data.add(value1);
                    data.add(value2);
                    return data;
                }
            };
        }
    }
}
