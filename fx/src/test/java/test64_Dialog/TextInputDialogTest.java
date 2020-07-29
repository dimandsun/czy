package test64_Dialog;

import com.czy.fx.test.FXUtil;
import javafx.application.Application;
import javafx.scene.control.Button;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * @author chenzy
 * @since 2020-06-05
 */
public class TextInputDialogTest extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        var anchorPane = new AnchorPane();
        var btn = new Button();
        btn.setOnAction(event -> {
            var inputDialog=new TextInputDialog("默认值");
            inputDialog.show();
        });
        anchorPane.getChildren().addAll(btn);
        FXUtil.setDefaultValue(primaryStage,anchorPane);
    }
}
