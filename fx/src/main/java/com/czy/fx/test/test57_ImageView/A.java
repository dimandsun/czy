package com.czy.fx.test.test57_ImageView;

import com.czy.fx.test.FXUtil;
import javafx.application.Application;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * @author chenzy
 * @since 2020-06-15
 */
public class A extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        var anchorPane = new AnchorPane();
        var image = new Image("girl.png");
        var imageView = new ImageView(image);
        Double width = image.getWidth();
        Double height = image.getHeight();

        var btn = new Button("还原");
        var btn2 = new Button("解析");
        var pixelReader = image.getPixelReader();
        var writableImage = new WritableImage(pixelReader, width.intValue(), height.intValue());
        var pixelWriter=writableImage.getPixelWriter();

        btn.setOnAction(event -> {
            imageView.setImage(image);
        });
        btn2.setOnAction(event -> {
            for (int i = 0; i < width.intValue(); i++) {
                for (int j = 0; j < height.intValue(); j++) {
                    var color=pixelReader.getColor(i,j);
                    var red=(int)(color.getRed()*255);
                    var green=(int)(color.getGreen()*255);
                    var blue=(int)(color.getBlue()*255);
                    if (red!=0&&green!=0&&blue!=0){
                        pixelWriter.setColor(i,j, Color.rgb(red,green,blue,color.getOpacity()));
                    }
                }
            }
            imageView.setImage(writableImage);
        });
        Color colr = null;
        colr.darker();
        anchorPane.getChildren().addAll(btn, btn2, imageView);
        FXUtil.setDefaultValue(primaryStage, anchorPane);
        AnchorPane.setLeftAnchor(btn2, btn.getWidth() + 10);
        AnchorPane.setTopAnchor(imageView, btn.getHeight() + 10);
    }
}
