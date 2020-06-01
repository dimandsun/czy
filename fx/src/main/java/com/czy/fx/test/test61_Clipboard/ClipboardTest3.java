package com.czy.fx.test.test61_Clipboard;

import com.czy.fx.test.FXUtil;
import javafx.application.Application;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DataFormat;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;


/**
 * @author chenzy
 * @since 2020-05-28
 */
public class ClipboardTest3 extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {

        var anchorPane = new AnchorPane();
        var box = new HBox();
        box.setPrefWidth(300);
        box.setPrefHeight(200);
        box.setStyle("-fx-background-color:#ffff55");
        var imageView = new ImageView();
        imageView.setPreserveRatio(true);
        imageView.setFitWidth(300);
        box.getChildren().addAll(imageView);
        anchorPane.getChildren().addAll(box);


        box.setOnDragEntered(event -> {
            box.setBorder(new Border(new BorderStroke(Paint.valueOf("#FF0000"), BorderStrokeStyle.SOLID, new CornerRadii(0), new BorderWidths(2))));

        });
        box.setOnDragExited(event -> {
//    box.setBorder(new Border(new BorderStroke(Paint.valueOf("#FF000000"), BorderStrokeStyle.SOLID,new CornerRadii(0),new BorderWidths(2))));
            box.setBorder(null);
        });
        box.setOnDragOver(event -> {
            event.acceptTransferModes(event.getTransferMode());
        });
        box.setOnDragDropped(event -> {
            var dragboard = event.getDragboard();
            if (dragboard.hasFiles()) {
                var file=dragboard.getFiles().get(0);
                try {
                    var image=new Image(new FileInputStream(file));
                    //Image image = (Image) dragboard.getContent(DataFormat.IMAGE);
                    Image img=new Image("file:"+dragboard.getFiles().get(0).toString());
                    imageView.setImage(image);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

            }else if (dragboard.hasUrl()){
                var image=new Image(dragboard.getUrl());
                imageView.setImage(image);
                System.out.println(dragboard.getUrl());
            }
        });

        FXUtil.setDefaultValue(primaryStage, anchorPane);
        AnchorPane.setLeftAnchor(box, 200d);
        AnchorPane.setTopAnchor(box, 100d);


        var scene = primaryStage.getScene();

    }
}
