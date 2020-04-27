package com.czy.fx;

import com.czy.fx.test1_helloWorld.HelloWorld;
import com.czy.fx.test6_platform.PlatformTest;
import com.czy.fx.test6_scene.SceneTest;
import com.czy.fx.test7_screen.ScreenTest;
import javafx.application.Application;

/**
 * @author chenzy
 * @description
 * @since 2020/4/27
 */
public class Main {
    public static void main(String[] args) {
        Class entrance = HelloWorld.class;
//        entrance = Login.class;
        entrance = com.czy.fx.test3_css.Login.class;
        entrance= PlatformTest.class;
        entrance= ScreenTest.class;
        entrance= SceneTest.class;
        Application.launch(entrance,args);


    }

}
