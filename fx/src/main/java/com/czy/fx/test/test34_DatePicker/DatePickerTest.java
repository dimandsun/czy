package com.czy.fx.test.test34_DatePicker;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.time.LocalDate;

/**
 * @author chenzy
 * @description
 * @since 2020-05-15
 */
public class DatePickerTest extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        var ap = new AnchorPane();
        var dp = new DatePicker(LocalDate.now());
        dp.valueProperty().addListener((observable, oldValue, newValue) -> {
            System.out.println(newValue);
        });
        dp.setDayCellFactory(datePicker->{
            var dc=new DateCell(){
                @Override
                public void updateItem(LocalDate item, boolean empty) {
                    super.updateItem(item, empty);
//                    setGraphic(new Button("hei"));
                }
            };
            return dc;
        });
        ap.getChildren().add(dp);
        primaryStage.setScene(new Scene(ap));
        primaryStage.show();
    }
}
