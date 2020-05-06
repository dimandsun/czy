package com.czy.fx;

import com.czy.fx.test10_AnchorPane.AnchorPaneTest;
import com.czy.fx.test1_helloWorld.HelloWorld;
import com.czy.fx.test2_login.Login;
import com.czy.fx.test6_platform.PlatformTest;
import com.czy.fx.test6_scene.SceneTest;
import com.czy.fx.test7_screen.ScreenTest;
import com.czy.fx.test8_group.GroupTest;
import com.czy.fx.test9_button.ButtonTest;
import javafx.application.Application;

/**
 * @author chenzy
 * @description
 * @since 2020/4/27
 */
public class Main {

    public static void main(String[] args) {
        Class entrance = HelloWorld.class;
        entrance = Login.class;
        entrance = com.czy.fx.test3_css.Login.class;
        entrance= PlatformTest.class;
        entrance= ScreenTest.class;
        entrance= SceneTest.class;
        entrance= GroupTest.class;
        entrance= ButtonTest.class;
        entrance = AnchorPaneTest.class;
//        entrance = HelloWorld.class;
        Application.launch(entrance,args);


    }

}
