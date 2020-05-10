package com.czy.fx.test.test16_TextFlow;

import com.czy.fx.test.FXUtil;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

/**
 * @author chenzy
 * @since 2020-05-09
 * @description 文本流式布局
 */
public class TextFlowTest extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        var textFlow=new TextFlow();

        textFlow.getChildren().addAll(FXUtil.getObjectList(Text.class,"123大家好爱的发发富士达大叔打饭，这个能缩进吗","嘿嘿","我是陈志源"));
        textFlow.getChildren().forEach(node->{
            var text = (Text)node;
            text.setFont(Font.font(20));
            //无法复制文本
//            text.getStyleClass().add("copyablelabel");
//            text.setCursor(Cursor.HAND);
            //设值文本颜色
            text.setFill(Paint.valueOf("#ee1234"));
        });
//        textFlow.setEffect(Effect);
        primaryStage.setScene(new Scene(textFlow));
        primaryStage.show();
    }
}
