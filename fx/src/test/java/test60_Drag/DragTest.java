package test60_Drag;

import com.czy.fx.test.FXUtil;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.concurrent.atomic.AtomicReference;

/**
 * @author chenzy
 * @since 2020-05-28
 */
public class DragTest extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        var anchorPane = new AnchorPane();
        var btn = new Button("person");
        AtomicReference<Double> a = new AtomicReference<>();
        AtomicReference<Double> b = new AtomicReference<>();
        btn.setOnMousePressed(event -> {
            a.set(event.getX());
            b.set(event.getY());

        });
        btn.setOnMouseDragged(event -> {
            AnchorPane.setLeftAnchor(btn, event.getSceneX() - a.get());
            AnchorPane.setTopAnchor(btn, event.getSceneY() - b.get());
        });
        anchorPane.getChildren().add(btn);
        var background = new Background(new BackgroundFill(Paint.valueOf("#FFB5c5"), new CornerRadii(15), new Insets(15)));
        anchorPane.setBackground(background);
        anchorPane.setBorder(new Border(new BorderStroke(Color.valueOf("red"),BorderStrokeStyle.DASHED,new CornerRadii(15),new BorderWidths(1),new Insets(0))));

        //没有装饰
        primaryStage.initStyle(StageStyle.TRANSPARENT);

        FXUtil.setDefaultValue(primaryStage, anchorPane, 200d, 200d);
        var scene = primaryStage.getScene();

        AtomicReference<Double> a1 = new AtomicReference<>();
        AtomicReference<Double> b1 = new AtomicReference<>();
        scene.setOnMousePressed(event -> {
            a1.set(event.getSceneX() - primaryStage.getX());
            b1.set(event.getSceneY() - primaryStage.getY());
        });
        scene.setOnMouseDragged(event -> {
            primaryStage.setX(event.getSceneX() - a1.get());
            primaryStage.setY(event.getSceneY() - b1.get());
        });
        scene.setFill(Paint.valueOf("#ffffff00"));
    }
}
