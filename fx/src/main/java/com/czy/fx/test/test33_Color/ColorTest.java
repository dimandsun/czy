package com.czy.fx.test.test33_Color;

import com.czy.fx.test.FXUtil;
import javafx.application.Application;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.util.ArrayList;

/**
 * @author chenzy
 * @since 2020-06-15
 */
public class ColorTest extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        var gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        var btn = new Button();
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                var rectangle = new Rectangle(150, 150);

                rectangle.setFill(Paint.valueOf("#EDEDED"));
                gridPane.add(rectangle, j, i);
            }
        }
        var list=new ArrayList<Color>();
        list.add(Color.valueOf("#EE6AA7"));
        list.add(Color.valueOf("#EE6AA755"));
        list.add(Color.valueOf("0xEE6AA755"));
        list.add(Color.RED);
        list.add(Color.CADETBLUE);
        list.add(new Color(50/255,100/255,150/255,0.5));
        list.add(new Color(50.0/255,100.0/255,150.0/255,0.5));
        list.add(Color.rgb(50,100,150,0.5));
        //参数2饱和度，参数3不透明度
        list.add(Color.hsb(50,0.5,1,0.5));
        var color=Color.rgb(34,212,143,0.5);
        list.add(color);
        list.add(color.deriveColor(-100,1,1,1));
        list.add(Color.web("rgb(255,50%,50%)",0.5));
        list.add(Color.gray(0.5,0.5));
        list.add(Color.grayRgb(128,0.5));
        //更亮点
        list.add(color.brighter());
        //更暗点
        list.add(color.darker());
        //提高饱和度
        list.add(color.saturate());
        //降低饱和度
        list.add(color.desaturate());
        //去掉色相，变成灰
        list.add(color.grayscale());
        //翻转颜色，得到补色
        list.add(color.invert());

        color.getHue();
        color.getSaturation();
        color.getBrightness();
        color.isOpaque();
        for (int i = 0; i < list.size(); i++) {
            var rectangle=(Rectangle)gridPane.getChildren().get(i);
            rectangle.setFill(list.get(i));
        }

        FXUtil.setDefaultValue(primaryStage, gridPane);
    }
}
