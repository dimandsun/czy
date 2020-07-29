package test63_TextFont;

import com.czy.fx.test.FXUtil;
import javafx.application.Application;
import javafx.geometry.VPos;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.text.*;
import javafx.stage.Stage;

/**
 * @author chenzy
 * @since 2020-06-05
 */
public class TextFontTest extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        var anchorPane = new AnchorPane();
        var box = new VBox();
        var text = new Text("hello world");
        var font = new Font("Courier", 25);
        text.setFont(font);

        var text1 = new Text("hello world");
        var font1 = new Font("Impact", 25);
        text1.setFont(font1);


        var text2 = new Text("hello world");
        var font2 = Font.loadFont("file:/C:\\Windows\\Fonts/sylfaen.ttf",25);
        text2.setFont(font2);

        //获取电脑上所有字体
        Font.getFamilies().forEach(s ->  System.out.println(s));


        var text3 = new Text("hello world");
        var font3 = Font.font("Yu Gothic UI", FontWeight.BOLD, FontPosture.ITALIC,25);
        text3.setFont(font3);

        var text4 = new Text("hello world");
        var font4 = new Font("Courier", 25);
        text4.setFont(font4);
        //填充颜色
        text4.setFill(Paint.valueOf("red"));
        //描边颜色
        text4.setStroke(Paint.valueOf("#cd5c5c"));
        //描边宽度
        text4.setStrokeWidth(2);
        //抗锯齿
        text4.setSmooth(true);
        //下划线
        text4.setUnderline(true);
        //中间线
        text4.setStrikethrough(true);
        //字体平滑
        text4.setFontSmoothingType(FontSmoothingType.LCD);
        //中间对齐
        text4.setTextAlignment(TextAlignment.CENTER);
        //行间距
        text4.setLineSpacing(20);
        //用像素限制宽度
        text4.setWrappingWidth(200);
        text4.setX(100);
        text4.setY(100);
        //原点位置
        text4.setTextOrigin(VPos.CENTER);
        var text5 = new Text("hello world");
        var font5 = new Font("Courier", 25);
        text5.setFont(font5);

        box.getChildren().addAll(text, text1, text2, text3, text4, text5);

        anchorPane.getChildren().add(box);
        FXUtil.setDefaultValue(primaryStage, anchorPane);
    }
}
