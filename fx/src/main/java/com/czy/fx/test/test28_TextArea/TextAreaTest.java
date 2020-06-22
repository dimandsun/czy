package com.czy.fx.test.test28_TextArea;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 * @author chenzy
 *
 * @since 2020-05-11
 */
public class TextAreaTest extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        var gridPane=new GridPane();
        var textArea=new TextArea();
        /*自动换行*/
        textArea.setWrapText(true);
        /*设值几行几列，和设置宽高类似*/
        textArea.setPrefColumnCount(10);
        textArea.setPrefRowCount(10);

        /*追加文本*/
        textArea.appendText("");
        /*删除*/
//        textArea.deleteText();
        /*插入*/
//        textArea.insertText();
        /*替换*/
//        textArea.replaceText();
        /*全选*/
        textArea.selectAll();

//        textArea.selectPositionCaret();
        /*选中指定区域*/
//        textArea.selectRange();
        /**/
        textArea.home();
        textArea.selectHome();
        textArea.selectEnd();
        textArea.clear();

        /*把选中文本复制*/
        textArea.copy();

        /*移动滚轮*/
        textArea.setScrollLeft(20);
        /*获得焦点*/
        textArea.requestFocus();
        gridPane.getChildren().addAll(textArea);
        stage.setScene(new Scene(gridPane));
        stage.show();
    }
}
