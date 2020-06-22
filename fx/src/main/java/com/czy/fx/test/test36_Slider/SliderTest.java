package com.czy.fx.test.test36_Slider;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Slider;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * @author chenzy
 *
 * @since 2020-05-15
 */
public class SliderTest extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        var root = new AnchorPane();
        var slider = new Slider(0, 100, 40);

        /*刻度*/
        slider.setShowTickLabels(true);
        /*小刻度*/
        slider.setShowTickMarks(true);
        /*刻度间隔单位*/
        slider.setMajorTickUnit(1);
        slider.valueProperty().addListener((observable, oldValue, newValue) -> {
            System.out.println(newValue);
        });
        slider.valueChangingProperty().addListener((observable, oldValue, newValue) -> {
            System.out.println(newValue);
        });
    /*    slider.setLabelFormatter(new StringConverter<Double>() {
            @Override
            public String toString(Double object) {
                return null;
            }

            @Override
            public Double fromString(String string) {
                return null;
            }
        });*/
        root.getChildren().add(slider);

        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }
}
