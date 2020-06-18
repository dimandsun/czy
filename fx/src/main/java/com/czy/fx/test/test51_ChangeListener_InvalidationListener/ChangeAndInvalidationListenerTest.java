package com.czy.fx.test.test51_ChangeListener_InvalidationListener;

import javafx.application.Application;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.WeakInvalidationListener;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.stage.Stage;

/**
 * @author chenzy
 * @description 延迟计算：改变发生的时候，值并不是立即重新计算。值被请求后才进行重新计算
 * @since 2020-05-21
 */
public class ChangeAndInvalidationListenerTest extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        var simpleIntegerProperty = new SimpleIntegerProperty(1);
      /*  simpleIntegerProperty.addListener((observable, oldValue, newValue) -> {
            println("{}->{}",oldValue,newValue);
        });*/

        var listener = new InvalidationListener() {
            @Override
            public void invalidated(Observable observable) {
                System.out.println("失效监听");
            }
        };
        /*不用会自动回收*/
        var weakInvalidationListener=new WeakInvalidationListener(listener);
        /*失效监听*/
        simpleIntegerProperty.addListener(weakInvalidationListener);
        simpleIntegerProperty.set(2);
        simpleIntegerProperty.set(3);
        simpleIntegerProperty.set(4);
//移除监听器
//        simpleIntegerProperty.removeListener(listener);
//        simpleIntegerProperty.get();
    }
}
