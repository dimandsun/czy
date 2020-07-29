package test42_ScrollPane;

import com.czy.fx.test.FXUtil;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * @author chenzy
 * 
 * @since 2020/5/17
 */
public class ScrollPaneTest extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        var anchorPane = new AnchorPane();

        var vBox = new VBox();
        vBox.getChildren().addAll(FXUtil.getButtonList("1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11"));
        var hBox = new HBox();
        hBox.getChildren().addAll(FXUtil.getButtonList("1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11"));
        var box = new VBox();
        box.getChildren().addAll(vBox,hBox);
        var scrollPane = new ScrollPane();
        scrollPane.setContent(box);
        anchorPane.getChildren().addAll(scrollPane);


        scrollPane.hvalueProperty().addListener((observable, oldValue, newValue) -> {
            System.out.println("hvalue:"+newValue);
        });
        scrollPane.vvalueProperty().addListener((observable, oldValue, newValue) -> {
            System.out.println("vvalue:"+newValue);
        });

        AnchorPane.setLeftAnchor(scrollPane, 100d);



        primaryStage.setScene(new Scene(anchorPane));
        primaryStage.show();
        scrollPane.setPrefHeight(box.getHeight());
        primaryStage.setWidth(1000);
        primaryStage.setHeight(anchorPane.getHeight());
    }
}
