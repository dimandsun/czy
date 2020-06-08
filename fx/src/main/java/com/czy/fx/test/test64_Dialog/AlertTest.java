package com.czy.fx.test.test64_Dialog;

import com.czy.fx.test.FXUtil;
import javafx.application.Application;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * @author chenzy
 * @since 2020-06-05
 */
public class AlertTest extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        var anchorPane = new AnchorPane();
        var btn = new Button();
        btn.setOnAction(event -> {
            var alert = new Alert(Alert.AlertType.CONFIRMATION );
            Button okButton = (Button) alert.getDialogPane().lookupButton(ButtonType.OK);
            okButton.setOnAction(event1 -> {
                alert.getTitle();
            });
            alert.setTitle("标题");
            alert.setHeaderText("内容标题");
            alert.setContentText("内容");
            alert.setGraphic(new Button("图标"));
            //置顶，只允许操作当前窗口
            alert.initModality(Modality.APPLICATION_MODAL);
            alert.show();


        });
        anchorPane.getChildren().addAll(btn);
        FXUtil.setDefaultValue(primaryStage, anchorPane);

    }
}
