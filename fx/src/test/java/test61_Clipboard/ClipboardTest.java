package test61_Clipboard;

import com.czy.fx.test.FXUtil;
import javafx.application.Application;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;


/**
 * @author chenzy
 * @since 2020-05-28
 */
public class ClipboardTest extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        var clipboard = Clipboard.getSystemClipboard();
        var clipboardContent=new ClipboardContent();
        clipboardContent.put(DataFormat.PLAIN_TEXT,"何灵辣豆腐");
        clipboard.setContent(clipboardContent);

//        clipboard.clear();

        var anchorPane = new AnchorPane();
        var label = new Label("等待粘贴内容");
        var imageView=new ImageView();
        imageView.setPreserveRatio(true);
        imageView.setFitWidth(300);

        anchorPane.getChildren().addAll(label,imageView);
        FXUtil.setDefaultValue(primaryStage, anchorPane);
        var keyCodeCombination = new KeyCodeCombination(KeyCode.V, KeyCombination.SHORTCUT_DOWN);
        var scene = primaryStage.getScene();
        scene.getAccelerators().put(keyCodeCombination, () -> {
            if (clipboard.hasFiles()){
                try {
                    imageView.setImage(new Image(new FileInputStream(clipboard.getFiles().get(0))));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }else if (clipboard.hasString()) {
                label.setText(clipboard.getString());
                clipboard.getContent(DataFormat.PLAIN_TEXT).toString();
            }
        });

    }
}
