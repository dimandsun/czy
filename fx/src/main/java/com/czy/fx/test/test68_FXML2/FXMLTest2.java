package com.czy.fx.test.test68_FXML2;

import com.czy.fx.test.FXUtil;
import com.czy.user.model.User;
import com.czy.util.io.FileUtil;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * @author chenzy
 * @since 2020-06-11
 */
public class FXMLTest2 extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        var anchorPane = new AnchorPane();
        var fxmlLoader=new FXMLLoader();
        var file = FileUtil.getCodeFile("fx","com.czy.fx.test.test68_FXML2.User.fxml");
        fxmlLoader.setLocation(file.toURI().toURL());
        fxmlLoader.setBuilderFactory(type -> new UserBuilder());
        User user =fxmlLoader.load();

        System.out.println(user);

        FXUtil.setDefaultValue(primaryStage,anchorPane);
    }
}
