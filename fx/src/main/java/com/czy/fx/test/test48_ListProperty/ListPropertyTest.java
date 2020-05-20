package com.czy.fx.test.test48_ListProperty;

import com.czy.fx.test.FXUtil;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * @author chenzy
 * @description
 * @since 2020-05-20
 */
public class ListPropertyTest extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        var anchorPane = new AnchorPane();

        FXUtil.setDefaultValue(primaryStage,anchorPane);
    }
}
