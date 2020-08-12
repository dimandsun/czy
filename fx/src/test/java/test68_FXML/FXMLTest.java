package test68_FXML;

import com.czy.fx.test.FXUtil;
import com.czy.util.io.FileUtil;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.FileInputStream;

/**
 * @author chenzy
 * @since 2020-06-10
 */
public class FXMLTest extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        var fxmlLoader=new FXMLLoader();
        var file = FileUtil.getCodeFile("fx","test68_FXML.fxml.fxml");
       AnchorPane anchorPane=fxmlLoader.load(new FileInputStream(file));
        Button btn= (Button) anchorPane.lookup("#buttonId");
        MyController controller=fxmlLoader.getController();
        FXUtil.setDefaultValue(primaryStage,anchorPane);
    }
}
