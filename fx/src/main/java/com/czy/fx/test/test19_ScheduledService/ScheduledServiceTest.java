package com.czy.fx.test.test19_ScheduledService;

import javafx.application.Application;
import javafx.concurrent.ScheduledService;
import javafx.concurrent.Task;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * @author chenzy
 * @description 多任务：
 * Platform是任务队列，ui线程空闲时执行，容易造成堵塞,
 * ScheduledService在call()中执行非UI任务，返回结果
 *                  在updateValue()中执行UI任务，接受call的返回值
 *                  cancel()取消任务(ui任务和非ui任务都会取消)
 * @since 2020-05-09
 */
public class ScheduledServiceTest extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        var ap = new AnchorPane();
        var ss = new ScheduledService<Integer>() {
            @Override
            protected Task<Integer> createTask() {
                return new Task<>() {
                    @Override
                    protected Integer call() throws Exception {
                        //这里不是UI线程
                        System.out.println("call:" + Thread.currentThread().getName());
                        return 0;
                    }

                    @Override
                    protected void updateValue(Integer value) {
                        super.updateValue(value);

                        System.out.println("updateValue:" + Thread.currentThread().getName());
                        //这里是UI线程
                    }
                };
            }
        };
        var btn = new Button();
        btn.setOnAction(event -> {
            //延迟0毫秒
            ss.setDelay(Duration.millis(0));
            //间隔1000毫秒执行一次
            ss.setPeriod(Duration.millis(1000));
            ss.start();
        });
        ap.getChildren().add(btn);
        primaryStage.setScene(new Scene(ap));
        primaryStage.show();
    }
}
