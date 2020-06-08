package com.czy.fx.test.test64_Dialog;

import com.czy.fx.test.FXUtil;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * @author chenzy
 * @since 2020-06-05
 */
public class CustomDialogTest extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        var anchorPane = new AnchorPane();
        var btn = new Button("自定义弹窗");
        btn.setOnAction(event -> {
            var dialog = new Stage();
            dialog.initOwner(primaryStage);
            dialog.initModality(Modality.WINDOW_MODAL);
            dialog.initStyle(StageStyle.UTILITY);
            dialog.setAlwaysOnTop(true);
            var anchorPane2 = new AnchorPane();
            anchorPane2.getChildren().addAll(new Button("呵呵"));
            dialog.setScene(new Scene(anchorPane2));
            dialog.show();
        });
        anchorPane.getChildren().addAll(btn);
        FXUtil.setDefaultValue(primaryStage, anchorPane);
    }
}
