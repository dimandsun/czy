package com.czy.fx.test;

import com.czy.fx.test.test10_AnchorPane.AnchorPaneTest;
import com.czy.fx.test.test11_HBoxVBox.HBoxTest;
import com.czy.fx.test.test12_BorderPane.BorderPaneTest;
import com.czy.fx.test.test13_FlowPane.FlowPaneTest;
import com.czy.fx.test.test14_GridPane.GridPaneTest;
import com.czy.fx.test.test15_StackPane.StackPaneTest;
import com.czy.fx.test.test16_TextFlow.TextFlowTest;
import com.czy.fx.test.test18_DialogPane.DialogPaneTest;
import com.czy.fx.test.test19_ScheduledService.ScheduledServiceTest;
import com.czy.fx.test.test1_helloWorld.HelloWorld;
import com.czy.fx.test.test21_MenuBar_Menu_MenuItem.MenuAboutTest;
import com.czy.fx.test.test22_media.MediaTest;
import com.czy.fx.test.test25_TitledPane.TitledPaneTest;
import com.czy.fx.test.test26_TabPane.TabPaneTest;
import com.czy.fx.test.test27_RadioButton_CheckBox.RadioButtonTest;
import com.czy.fx.test.test28_TextArea.TextAreaTest;
import com.czy.fx.test.test29_TextField.TextFieldTest;
import com.czy.fx.test.test2_login.Login;
import com.czy.fx.test.test30_ButtonBar.ButtonBarTest;
import com.czy.fx.test.test31_ChoiceBox.ChoiceBoxTest;
import com.czy.fx.test.test32_ComboBox.ComboBoxTest;
import com.czy.fx.test.test33_ColorPicke.ColorPickeTest;
import com.czy.fx.test.test34_DatePicker.DatePickerTest;
import com.czy.fx.test.test35_Pagination.PaginationTest;
import com.czy.fx.test.test36_Slider.SliderTest;
import com.czy.fx.test.test37_ProgressBar.ProgressBarTest;
import com.czy.fx.test.test38_ProgressIndicator.ProgressIndicatorTest;
import com.czy.fx.test.test6_platform.PlatformTest;
import com.czy.fx.test.test6_scene.SceneTest;
import com.czy.fx.test.test7_screen.ScreenTest;
import com.czy.fx.test.test8_group.GroupTest;
import com.czy.fx.test.test9_button.ButtonTest;
import com.czy.util.FileUtil;
import com.czy.util.StringUtil;
import javafx.application.Application;

import java.io.File;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

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
        entrance = PlatformTest.class;
        entrance = ScreenTest.class;
        entrance = SceneTest.class;
        entrance = GroupTest.class;
        entrance = ButtonTest.class;
        entrance = AnchorPaneTest.class;
        entrance = HBoxTest.class;
        entrance = BorderPaneTest.class;
        entrance = FlowPaneTest.class;
        entrance = GridPaneTest.class;
        entrance = StackPaneTest.class;
        entrance = TextFlowTest.class;
        entrance = DialogPaneTest.class;
        entrance = ScheduledServiceTest.class;
        entrance = MenuAboutTest.class;
        entrance = TitledPaneTest.class;
        entrance = TabPaneTest.class;
        entrance = RadioButtonTest.class;
        entrance = TextAreaTest.class;
        entrance = TextFieldTest.class;
        entrance= ButtonBarTest.class;
        entrance= ChoiceBoxTest.class;
        entrance= ComboBoxTest.class;
        entrance= ColorPickeTest.class;
        entrance= DatePickerTest.class;
        entrance= PaginationTest.class;
        entrance= SliderTest.class;
        entrance= MediaTest.class;
        entrance= ProgressBarTest.class;
        entrance= ProgressIndicatorTest.class;
        Application.launch(entrance,args);

    }



    private static void createDirs() {
        //        ,"setCellFactory"
        String[] ss = {"Hyperlink", "MenuBar_Menu_MenuItem", "chm", "MenuItem", "Accordion", "TitledPane"
                , "TabPane", "RadioButton_CheckBox", "TextArea", "TextField", "ButtonBar", "ChoiceBox", "ComboBox"
                , "ColorPicke", "DatePicker", "Pagination", "Slider", "ProgressBar", "ProgressIndicator", "SplitPane"
                , "Spinner", "ScrollBar", "ScrollPane", "Separator", "PropertyChangeSupport", "ListProperty", "SetProperty", "MapProperty"
                , "ChangeListener", "InvalidationListener"};

        String beanPackage = "com.czy.fx.test.test";
        String moduleDir = "fx";
        Integer i = 20;
        for (String s : ss) {
            File modelDir = FileUtil.getCodeFile(moduleDir, beanPackage + i + "_" + s);
            if (!modelDir.exists()) {
                modelDir.mkdirs();
            }
            modelDir.getPath();
            i++;
        }


    }


}
