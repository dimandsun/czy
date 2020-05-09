package com.czy.fx.test;

import com.czy.fx.test.test10_AnchorPane.AnchorPaneTest;
import com.czy.fx.test.test11_HBoxVBox.HBoxTest;
import com.czy.fx.test.test12_BorderPane.BorderPaneTest;
import com.czy.fx.test.test13_FlowPane.FlowPaneTest;
import com.czy.fx.test.test14_GridPane.GridPaneTest;
import com.czy.fx.test.test1_helloWorld.HelloWorld;
import com.czy.fx.test.test2_login.Login;
import com.czy.fx.test.test6_platform.PlatformTest;
import com.czy.fx.test.test6_scene.SceneTest;
import com.czy.fx.test.test7_screen.ScreenTest;
import com.czy.fx.test.test8_group.GroupTest;
import com.czy.fx.test.test9_button.ButtonTest;
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
        entrance = com.czy.fx.test.test3_css.Login.class;
        entrance= PlatformTest.class;
        entrance= ScreenTest.class;
        entrance= SceneTest.class;
        entrance= GroupTest.class;
        entrance= ButtonTest.class;
        entrance = AnchorPaneTest.class;
        entrance = HBoxTest.class;
        entrance = BorderPaneTest.class;
        entrance= FlowPaneTest.class;
        entrance= GridPaneTest.class;
        Application.launch(entrance,args);


    }

}
