package com.czy.fx.test.test55_Tooltip;

import com.czy.fx.myfx.Mybutton;
import com.czy.fx.test.FXUtil;
import javafx.application.Application;
import javafx.scene.control.OverrunStyle;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.TextAlignment;
import javafx.stage.PopupWindow;
import javafx.stage.Stage;

/**
 * @author chenzy
 * @description
 * @since 2020-05-23
 */
public class TooltipTest extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        var anchorPane = new AnchorPane();
        var btn = new Mybutton();
        var tooltip=new Tooltip("21123234aadsads");
        btn.setTooltip(tooltip);
        /*超出边界的文本怎么显示：CLIP不显示，*/
        tooltip.setTextOverrun(OverrunStyle.CLIP);
        /*多行生效*/
        tooltip.setTextAlignment(TextAlignment.CENTER);
        /*方向*/
        tooltip.setAnchorLocation(PopupWindow.AnchorLocation.CONTENT_BOTTOM_LEFT);
        /*解除关联、关联*/
//        Tooltip.uninstall(btn,tooltip);
//        Tooltip.install(btn,tooltip);
        tooltip.setOnHidden(event -> {
            System.out.println("OnHidden");
        });
        tooltip.setOnShown(event -> {
            System.out.println("OnShown");
        });
        var tooltip2=new Tooltip("21123234aadsads");

        /*自动隐藏*/
        tooltip2.setAutoHide(true);
        /**/
//        tooltip2.setX(1);
//        tooltip2.setY(2);
        anchorPane.getChildren().addAll(btn);
        FXUtil.setDefaultValue(primaryStage,anchorPane);
        /*场景显示时就显示提示，且不会自动隐藏,需要设置*/
        tooltip2.show(primaryStage);
    }
}
