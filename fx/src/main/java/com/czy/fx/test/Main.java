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
import com.czy.fx.test.test19_ScheduledService.ServiceTest;
import com.czy.fx.test.test19_ScheduledService.TaskTest;
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
import com.czy.fx.test.test33_Color.ColorPickeTest;
import com.czy.fx.test.test34_DatePicker.DatePickerTest;
import com.czy.fx.test.test35_Pagination.PaginationTest;
import com.czy.fx.test.test36_Slider.SliderTest;
import com.czy.fx.test.test37_ProgressBar.ProgressBarTest;
import com.czy.fx.test.test38_ProgressIndicator.ProgressIndicatorTest;
import com.czy.fx.test.test39_SplitPane.SplitPaneTest;
import com.czy.fx.test.test40_Spinner.SpinnerTest;
import com.czy.fx.test.test41_ScrollBar.ScrollBarTest;
import com.czy.fx.test.test42_ScrollPane.ScrollPaneTest;
import com.czy.fx.test.test43_Separator.SeparatorTest;
import com.czy.fx.test.test44_Point2D.Point2DTest;
import com.czy.fx.test.test45_PropertyChangeSupport.PropertyChangeSupportTest;
import com.czy.fx.test.test46_SimpleIntegerProperty.SimpleIntegerPropertyTest;
import com.czy.fx.test.test47_ReadOnlyDoubleWrapper.ReadOnlyDoubleWrapperTest;
import com.czy.fx.test.test48_ListProperty.ListPropertyTest;
import com.czy.fx.test.test49_SetProperty.SetPropertyTest;
import com.czy.fx.test.test51_ChangeListener_InvalidationListener.ChangeAndInvalidationListenerTest;
import com.czy.fx.test.test52_binding.BindingTest;
import com.czy.fx.test.test54_FileChooser.FileChooserTest;
import com.czy.fx.test.test55_Tooltip.TooltipTest;
import com.czy.fx.test.test56_Image.ImageTest;
import com.czy.fx.test.test57_ImageView.ImageViewTest;
import com.czy.fx.test.test58_event.EventTest;
import com.czy.fx.test.test59_Filter.FilterTest;
import com.czy.fx.test.test60_Drag.DragTest;
import com.czy.fx.test.test61_Clipboard.ClipboardTest;
import com.czy.fx.test.test61_Clipboard.ClipboardTest2;
import com.czy.fx.test.test61_Clipboard.ClipboardTest3;
import com.czy.fx.test.test62_ListView.ListViewTestBeanList;
import com.czy.fx.test.test62_ListView.ListViewTestStringList;
import com.czy.fx.test.test62_ListView.ListViewTestStringList2;
import com.czy.fx.test.test63_TextFont.TextFontTest;
import com.czy.fx.test.test64_Dialog.*;
import com.czy.fx.test.test65_TableView.TableViewTest;
import com.czy.fx.test.test66_TreeView.TreeViewTest;
import com.czy.fx.test.test66_TreeView.TreeViewTest2;
import com.czy.fx.test.test66_TreeView.TreeViewTest3;
import com.czy.fx.test.test67_TreeTableView.TreeTableViewTest;
import com.czy.fx.test.test68_FXML.FXMLTest;
import com.czy.fx.test.test68_FXML2.FXMLTest2;
import com.czy.fx.test.test69_GraspData.GraspData;
import com.czy.fx.test.test6_platform.PlatformTest;
import com.czy.fx.test.test6_scene.SceneTest;
import com.czy.fx.test.test70_screenshot.ScreenshotTest;
import com.czy.fx.test.test71_ResourceBundle.ResourceBundleTest;
import com.czy.fx.test.test73_Transform.*;
import com.czy.fx.test.test74_Chart.BarChartTest;
import com.czy.fx.test.test74_Chart.LineChartTest;
import com.czy.fx.test.test74_Chart.PieChartTest;
import com.czy.fx.test.test7_screen.ScreenTest;
import com.czy.fx.test.test8_group.GroupTest;
import com.czy.fx.test.test9_button.ButtonTest;
import com.czy.util.io.FileUtil;
import javafx.application.Application;

import java.io.File;

/**
 * @author chenzy
 *
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
        entrance = ButtonBarTest.class;
        entrance = ChoiceBoxTest.class;
        entrance = ComboBoxTest.class;
        entrance = ColorPickeTest.class;
        entrance = DatePickerTest.class;
        entrance = PaginationTest.class;
        entrance = SliderTest.class;
        entrance = MediaTest.class;
        entrance = ProgressBarTest.class;
        entrance = ProgressIndicatorTest.class;
        entrance = SplitPaneTest.class;
        entrance = SpinnerTest.class;
        entrance = ScrollBarTest.class;
        entrance = ScrollPaneTest.class;
        entrance = SeparatorTest.class;
        entrance = Point2DTest.class;
        entrance = PropertyChangeSupportTest.class;
        entrance = SimpleIntegerPropertyTest.class;
        entrance = ReadOnlyDoubleWrapperTest.class;
        entrance = ListPropertyTest.class;
        entrance = SetPropertyTest.class;
        entrance = ChangeAndInvalidationListenerTest.class;
        entrance = BindingTest.class;
        entrance = FileChooserTest.class;
        entrance = TooltipTest.class;
        entrance = ImageTest.class;
        entrance = ImageViewTest.class;
        entrance = EventTest.class;
        entrance = FilterTest.class;
        entrance = DragTest.class;
        entrance = ClipboardTest.class;
        entrance = ClipboardTest2.class;
        entrance = ClipboardTest3.class;
        entrance = ListViewTestBeanList.class;
        entrance = ListViewTestStringList.class;
        entrance = ClipboardTest2.class;
        entrance = ListViewTestStringList2.class;
        entrance = TextFontTest.class;
        entrance = DialogTest.class;
        entrance = AlertTest.class;
        entrance = ChoiceDialogTest.class;
        entrance = TextInputDialogTest.class;
        entrance = CustomDialogTest.class;
        entrance = TableViewTest.class;
        entrance = TreeViewTest.class;
        entrance = TreeViewTest3.class;
        entrance = TreeViewTest2.class;
        entrance = TreeTableViewTest.class;
        entrance = TaskTest.class;
        entrance = ServiceTest.class;
        entrance = FXMLTest.class;
        entrance = FXMLTest2.class;
        entrance = GraspData.class;
        entrance = ScreenshotTest.class;
        entrance = ResourceBundleTest.class;
        entrance = TranslateTest.class;
        entrance = ScaleTest.class;
        entrance = RotateTest.class;
        entrance = ShearTest.class;
        entrance = AffineTest.class;
        entrance= PieChartTest.class;
        entrance= BarChartTest.class;
        entrance= LineChartTest.class;
        Application.launch(entrance, args);
//        createDirs();


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
        ss = new String[]{"SimpleIntegerProperty", "ReadOnlyDoubleWrapper", "ListProperty", "SetProperty", "MapProperty"
                , "ChangeListener", "InvalidationListener"};
        Integer i = 46;
        ss = new String[]{"FileChooser", "Tooltip", "Image", "ImageView", "Pixel"
                , "ImageIO"};
        i = 54;
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
