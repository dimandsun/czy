package test70_screenshot;

import com.czy.fx.test.FXUtil;
import com.czy.util.io.FileUtil;
import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * @author chenzy
 * @since 2020-06-11
 */
public class ScreenshotTest extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        var anchorPane = new AnchorPane();
        var btn = new Button("截图");
        var btn2 = new Button("保存截图");

        var imageView = new ImageView();
        imageView.setPreserveRatio(true);
        btn.setOnAction(event -> {
            //最小化到任务栏
            primaryStage.setIconified(true);

            var stage = new Stage();
            var root = new AnchorPane();
            stage.setScene(new Scene(root));
            stage.setAlwaysOnTop(true);
            stage.setFullScreen(true);
            //透明度设值
            stage.initStyle(StageStyle.TRANSPARENT);
            stage.getScene().setFill(Paint.valueOf("#b5b5b522"));
            root.setStyle("-fx-background-color: #ffff5522");

            stage.setFullScreenExitHint(""); //退出全屏时的提示
            stage.getScene().setOnKeyPressed(keyEvent -> {
                //按esc键退出截屏
                if (keyEvent.getCode().equals(KeyCode.ESCAPE)) {
                    stage.close();
                    primaryStage.setIconified(false);
                }
            });
            stage.show();

            root.getChildren().clear();
            var view = new HBox();
            root.setOnMousePressed(event1 -> {
                xStart = event1.getSceneX();
                yStart = event1.getSceneY();

                view.setBackground(null);
                view.setBorder(new Border(new BorderStroke(Paint.valueOf("#cd3700"), BorderStrokeStyle.SOLID, null, new BorderWidths(2))));
                root.getChildren().add(view);
                AnchorPane.setLeftAnchor(view, xStart);
                AnchorPane.setTopAnchor(view, yStart);

            });
            root.setOnDragDetected(event1 -> {
                root.startFullDrag();
            });
            root.setOnMouseDragOver(event1 -> {
                var label = new Label("adffad");
                label.setTextFill(Paint.valueOf("#ffffff"));
                label.setStyle("-fx-background-color:#000000");
                label.setAlignment(Pos.CENTER);
                label.setPrefWidth(160);
                label.setPrefHeight(30);
                root.getChildren().add(label);
                AnchorPane.setLeftAnchor(label, xStart);
                AnchorPane.setTopAnchor(label, yStart - label.getPrefHeight());

                view.setPrefWidth(event1.getSceneX() - xStart);
                view.setPrefHeight(event1.getSceneY() - yStart);

            });
            root.setOnMouseDragExited(event1 -> {
                xEnd = event1.getSceneX();
                yEnd = event1.getSceneY();
                var b = new Button("完成");
                view.getChildren().add(b);
                view.setAlignment(Pos.BOTTOM_RIGHT);
                b.setOnAction(event2 -> {
                    stage.close();
                    var w = xEnd - xStart;
                    var h = yEnd - yStart;
                    Robot robot = null;
                    try {
                        robot = new Robot();
                    } catch (AWTException e) {
                        e.printStackTrace();
                    }
//                    bufferedImage = robot.createScreenCapture(new Rectangle(xStart.intValue(), yStart.intValue(), (int) w, (int) h));
                    var writableImage = SwingFXUtils.toFXImage(bufferedImage, null);
                    imageView.setImage(writableImage);
                    primaryStage.setIconified(false);

                    var clipboard = Clipboard.getSystemClipboard();
                    var clipboardContent = new ClipboardContent();
                    clipboardContent.putImage(writableImage);
                    clipboard.setContent(clipboardContent);
                });
            });
        });
        btn2.setOnAction(event -> {
            try {
                var file= FileUtil.getResourceFile("fx","abc.png");
                ImageIO.write(bufferedImage,"png",file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        anchorPane.getChildren().addAll(btn,btn2, imageView);
        primaryStage.setTitle("简单截图");
        FXUtil.setDefaultValue(primaryStage, anchorPane);
        AnchorPane.setLeftAnchor(btn2,btn.getWidth());
        AnchorPane.setTopAnchor(imageView,btn.getHeight());

    }
    BufferedImage bufferedImage = null;
    Double xStart = null;
    Double xEnd = null;
    Double yStart = null;
    Double yEnd = null;
}
