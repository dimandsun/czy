package com.czy.fx.test.test64_Dialog;

import com.czy.fx.test.FXUtil;
import javafx.application.Application;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.util.Optional;
import java.util.function.Consumer;

/**
 * @author chenzy
 * @since 2020-06-05
 * 对话框
 */
public class DialogTest extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        var anchorPane = new AnchorPane();
        var btn = new Button("弹出窗");
        btn.setOnAction(event -> {
            var dialog = new Dialog<ButtonType>();
            dialog.setTitle("弹出窗标题");
            dialog.setContentText("弹出窗练习");
            dialog.setGraphic(new ImageView("qq.jpg"));
            dialog.getDialogPane().getButtonTypes().addAll(ButtonType.CANCEL, ButtonType.OK);
            Button okButton = (Button) dialog.getDialogPane().lookupButton(ButtonType.OK);
            Button cancelButton = (Button) dialog.getDialogPane().lookupButton(ButtonType.CANCEL);
            //            移除按钮
//            alert.getDialogPane().getButtonTypes().remove();

            cancelButton.setOnAction(event1 -> {
                System.out.println("cancelButton");
            });
            okButton.setOnAction(event1 -> {
                System.out.println("ok");
            });
            dialog.setOnCloseRequest(event1 -> {
                System.out.println(event1);
            });
            dialog.setResultConverter(param -> {
                System.out.println(param);
                return param;

            });
            Optional<ButtonType> buttonType = dialog.showAndWait();
            buttonType.ifPresent(buttonType1 -> {
                if (buttonType1.getButtonData() == ButtonBar.ButtonData.OK_DONE) {
                    dialog.setContentText("heieh1,你点了ok");
                    System.out.println("123");
                } else {
                    dialog.setContentText("heieh1");
                    System.out.println("2");
                }
            });
            dialog.show();

        });
        anchorPane.getChildren().addAll(btn);
        FXUtil.setDefaultValue(primaryStage, anchorPane);

    }
}
