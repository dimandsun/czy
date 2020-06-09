package com.czy.fx.test.test19_ScheduledService;

import com.czy.fx.test.FXUtil;
import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * @author chenzy
 * @since 2020-06-09
 * task一次性任务
 * service
 */
public class TaskTest extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        var anchorPane = new AnchorPane();
        var btn = new Button("任务开始");
        btn.setUserData(true);
        var progressBar = new ProgressBar(0);


        var task=new Task<Number>(){
            //UI线程
            @Override
            protected void updateValue(Number value) {
                super.updateValue(value);
//                progressBar.setProgress(value.doubleValue()+0.1);
//                System.out.println(progressBar.getProgress());
            }
            //非UI线程
            @Override
            protected Number call() throws InterruptedException {
                System.out.println(Thread.currentThread().getName());
                for (int i=0;i<100;i++){
                    if (isCancelled()){
                        return 0;
                    }
                    updateProgress(i,100);
                    Thread.sleep(100);
                }
                return 0;
            }
        };
        task.progressProperty().addListener((observable, oldValue, newValue) -> {
            progressBar.setProgress(newValue.doubleValue());
//            System.out.println(Thread.currentThread().getName()+":"+newValue.doubleValue());
        });
        task.stateProperty().addListener((observable, oldValue, newValue) -> {
            System.out.println(newValue);
        });
        //
        task.exceptionProperty().addListener((observable, oldValue, newValue) -> {

        });
        btn.setOnAction(event -> {
            Boolean isBegin = (Boolean) btn.getUserData();
            if (isBegin){
                new Thread(task).start();
//                task.run();
                btn.setText("取消任务");
            }else {
                task.cancel();
                btn.setText("任务开始");
            }
            btn.setUserData(!isBegin);
        });

        anchorPane.getChildren().addAll(btn, progressBar);

        FXUtil.setDefaultValue(primaryStage, anchorPane);
        AnchorPane.setLeftAnchor(progressBar, btn.getWidth()+10);
    }
}
