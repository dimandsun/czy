package test37_ProgressBar;

import javafx.application.Application;
import javafx.concurrent.ScheduledService;
import javafx.concurrent.Task;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * @author chenzy
 * 
 * @since 2020/5/16
 */
public class ProgressBarTest extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        var ap = new AnchorPane();
        var progressBar=new ProgressBar();
        progressBar.setProgress(0.5);
        //不确定进度
        progressBar.setProgress(ProgressBar.INDETERMINATE_PROGRESS);

        var scheduledService=new ScheduledService<Double>(){
            Double i=0d;
            @Override
            protected Task<Double> createTask() {
                return new Task<>(){
                    @Override
                    protected Double call() throws Exception {
                        if (i>=1){
                            i=0d;
                        }
                        return i+=0.1;
                    }

                    @Override
                    protected void updateValue(Double value) {
                        super.updateValue(value);
                        progressBar.setProgress(value);
                    }
                };

            }
        };
        var btn = new Button();
        btn.setOnAction(event -> {
            scheduledService.start();
            scheduledService.setPeriod(Duration.seconds(1));
        });
        ap.getChildren().addAll(progressBar,btn);
        AnchorPane.setBottomAnchor(btn,10d);
        ap.setPrefHeight(100);
        primaryStage.setScene(new Scene(ap));
        primaryStage.show();
    }
}
