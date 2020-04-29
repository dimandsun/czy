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

import java.io.InputStream;
import java.util.Map;

/**
 * @author chenzy
 * @description
 * @since 2020/4/27
 */
public class Main {

    public static void main(String[] args) {
        String javafxPath ="D:\\soft\\Java\\javafx-sdk-11.0.2\\lib";
//        System.setProperty("jdk.module.path","D:\\soft\\Java\\javafx-sdk-11.0.2\\lib");
        for (Map.Entry aa:System.getProperties().entrySet()){
            System.out.println(aa.getKey()+":"+aa.getValue());
        }

        System.getProperties().put("jdk.module.path",javafxPath);
        Class entrance = HelloWorld.class;
        entrance = Login.class;
        entrance = com.czy.fx.test3_css.Login.class;
        entrance= PlatformTest.class;
        entrance= ScreenTest.class;
        entrance= SceneTest.class;
        entrance= GroupTest.class;
        entrance= ButtonTest.class;
        entrance = Login.class;
        entrance= AnchorPaneTest.class;
        Application.launch(entrance,args);


    }

}
