package test30_ButtonBar;

import com.czy.fx.test.FXUtil;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * @author chenzy
 * @since 2020-05-12
 * 
 */
public class ButtonBarTest extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        var ap=new AnchorPane();
        var bbtn = new ButtonBar();
        bbtn.getButtons().addAll(FXUtil.getButtonList("1","2"));
        ButtonBar.setButtonData(new Button(),ButtonBar.ButtonData.FINISH);

        ap.getChildren().addAll(bbtn);
        stage.setScene(new Scene(ap));
        stage.show();
    }
}
