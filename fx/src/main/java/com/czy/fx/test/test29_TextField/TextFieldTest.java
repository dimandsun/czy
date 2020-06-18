package com.czy.fx.test.test29_TextField;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.StringConverter;

/**
 * @author chenzy
 * @description
 * @since 2020-05-11
 */
public class TextFieldTest extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        var ap = new AnchorPane();

        var tt = new TextField();


        tt.setTextFormatter(new TextFormatter<String>((change) -> change));
        tt.setTextFormatter(new TextFormatter<String>(new StringConverter<String>() {
            @Override
            public String toString(String s) {

                System.out.println("1:"+tt.getText());
                return s+"11";
            }

            @Override
            public String fromString(String s) {

                System.out.println("2:"+tt.getText());
                return s+"22";
            }
        }));
//        tt.commitValue();
        
        tt.textProperty().addListener(((observableValue, s, t1) -> {

            System.out.println("3:"+tt.getText());
        }));
        ap.getChildren().add(tt);

        stage.setScene(new Scene(ap));
        stage.show();
    }
}
