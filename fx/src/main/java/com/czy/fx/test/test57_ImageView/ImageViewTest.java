package com.czy.fx.test.test57_ImageView;

import com.czy.fx.test.FXUtil;
import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;

/**
 * @author chenzy
 * @description
 * @since 2020-05-23
 */
public class ImageViewTest extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        var anchorPane = new AnchorPane();
        var imgView = new ImageView("qq.jpg");
        anchorPane.getChildren().addAll(imgView);

        /*在image中设置可以节省内存*/
        /*保持宽高比*/
        imgView.setPreserveRatio(true);
        /*更好的压缩质量*/
        imgView.setSmooth(true);
        imgView.setFitWidth(600);
        imgView.setFitHeight(600);


        /*真正宽高*/
        System.out.println(imgView.prefWidth(-1));
        System.out.println(imgView.prefHeight(-1));
        /*偏移量*/
        imgView.getContentBias();
        /*圆角图片*/
        var rectangle=new Rectangle(imgView.prefWidth(-1),imgView.prefHeight(-1));
        rectangle.setArcWidth(50);
        rectangle.setArcHeight(50);
        imgView.setClip(rectangle);
        imgView.setX(100);
        rectangle.setX(100);
        FXUtil.setDefaultValue(primaryStage,anchorPane);
    }
}
