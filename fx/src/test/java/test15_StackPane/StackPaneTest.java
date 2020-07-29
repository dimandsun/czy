package test15_StackPane;

import com.czy.fx.test.FXUtil;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.util.ArrayList;

/**
 * @author chenzy
 * @since 2020-05-09
 *
 */
public class StackPaneTest extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        var sp=new StackPane();

        sp.getChildren().addAll(FXUtil.getButtonList("1","2","3"));
        sp.setAlignment(Pos.BOTTOM_RIGHT);
        sp.setMargin(sp.getChildren().get(0),new Insets(100));
        primaryStage.setScene(new Scene(sp));

        new ArrayList<Button>().forEach(btn-> System.out.println(btn.getText()));
        primaryStage.show();
    }
}
