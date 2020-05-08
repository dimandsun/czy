package com.czy.fx.test.test6_platform;

import com.czy.util.StringUtil;
import javafx.application.Application;
import javafx.application.ConditionalFeature;
import javafx.application.Platform;
import javafx.stage.Stage;

/**
 * @author chenzy
 * @description
 * @since 2020/4/27
 */
public class PlatformTest extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        /*任务队列，并没有增加新线程，适用于闲时更新ui*/
        Platform.runLater(()->{
            StringUtil.println("Platform.runLater中的线程{}",Thread.currentThread().getName());
        });
        /*即使关闭全部窗口，也不会退出程序*/
        Platform.setImplicitExit(false);

        /*判断客户端是否支持某某技术,SCENE3D 3D*/
        Platform.isSupported(ConditionalFeature.SCENE3D);
        stage.show();
    }
}
