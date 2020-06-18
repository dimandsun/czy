package com.czy.fx.test.test19_ScheduledService;

import javafx.application.Application;
import javafx.concurrent.ScheduledService;
import javafx.concurrent.Task;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * @author chenzy
 * @description 多任务：
 * Platform是任务队列，ui线程空闲时执行，容易造成堵塞,
 * ScheduledService在call()中执行非UI任务，返回结果
 * 在updateValue()中执行UI任务，接受call的返回值
 * cancel()取消任务(ui任务和非ui任务都会取消)
 * @since 2020-05-09
 */
public class ScheduledServiceTest extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Hello World!");

        HBox hBox=new HBox(20);

        Button startBtn = new Button("开始");
        Button cancelBtn = new Button("取消");
        Button resetBtn = new Button("重置");
        Button restartBtn = new Button("重启");
        ProgressBar progressBar = new ProgressBar(0);
        progressBar.setPrefWidth(200);

        Label l1=new Label("state");
        Label l2=new Label("value");
        Label l3=new Label("title");
        Label l4=new Label("message");

        FlowPane root = new FlowPane();
        hBox.getChildren().addAll(startBtn,cancelBtn,restartBtn,resetBtn,progressBar,l1,l2,l3,l4);
        root.getChildren().add(hBox);
        primaryStage.setScene(new Scene(root, 700, 300));
        primaryStage.show();

        var scheduledService=new MyScheduledService();
        //等待5s开始、

        //任务失败后重启
        scheduledService.setRestartOnFailure(true);
        // 任务失败4次后不重启
        scheduledService.setMaximumFailureCount(4);
        //任务启动策略
//        scheduledService.setBackoffStrategy();
        //延迟0毫秒
        scheduledService.setDelay(Duration.millis(0));
        //间隔1000毫秒执行一次
        scheduledService.setPeriod(Duration.millis(1000));

        startBtn.setOnAction(event ->{
            scheduledService.start();
            System.out.println("开始");
        });
        cancelBtn.setOnAction(event ->{
            scheduledService.cancel();
            System.out.println("取消");
        });
        resetBtn.setOnAction(event -> {
            scheduledService.reset();
            System.out.println("重置");
        });
        restartBtn.setOnAction(event -> {
            scheduledService.restart();
            System.out.println("重启");
        });

        scheduledService.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue!=null){
                l2.setText(String.valueOf(newValue));
            }
        });

        scheduledService.lastValueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue!=null){
                System.out.println("lastValue="+newValue.intValue());
            }
        });


    }
    class MyScheduledService extends ScheduledService<Number>{

        int sum=0;
        @Override
        protected Task<Number> createTask() {

            System.out.println("createTask()");

            Task<Number> task=new Task<>() {

                @Override
                protected void updateValue(Number value) {
                    super.updateValue(value);
                    if (value.intValue()==10){
                        MyScheduledService.this.cancel();
                        System.out.println("任务取消");
                    }
                }

                @Override
                protected Number call() {
                    sum=sum+1;
                    System.out.println(sum);
                    return sum;
                }
            };
            return task;
        }
    }
}
