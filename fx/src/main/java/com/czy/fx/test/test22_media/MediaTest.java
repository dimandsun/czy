package com.czy.fx.test.test22_media;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

import java.io.File;

/**
 * @author chenzy
 * @description
 * @since 2020-05-16
 */
public class MediaTest extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        var ap=new AnchorPane();
        var btn = new Button("播放");
        String source = "https://other.web.ri03.sycdn.kuwo.cn/resource/a1/93/1/1538412850.aac";
        source="file:/E:/CloudMusic/Fine乐团配不上你.mp3";
        var mediaPaly = new MediaPlayer(new Media(source));
        btn.setOnAction(event -> {

            mediaPaly.play();

        });
        ap.getChildren().add(btn);
        primaryStage.setScene(new Scene(ap));
        primaryStage.show();
    }
}
