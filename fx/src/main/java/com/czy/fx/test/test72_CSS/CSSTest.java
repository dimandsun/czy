package com.czy.fx.test.test72_CSS;

import com.czy.fx.test.FXUtil;
import javafx.application.Application;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * @author chenzy
 * @since 2020-06-12
 */
public class CSSTest extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {

        Application.setUserAgentStylesheet(STYLESHEET_CASPIAN);

        var url=this.getClass().getClassLoader().getResource("com/czy/fx/test/test71_ResourceBundle/a.css");
        var anchorPane = new AnchorPane();
        anchorPane.getStylesheets().add(url.toExternalForm());
        FXUtil.setDefaultValue(primaryStage,anchorPane);

    }
}
