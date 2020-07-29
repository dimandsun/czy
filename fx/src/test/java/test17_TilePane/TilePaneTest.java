package test17_TilePane;

import com.czy.fx.test.FXUtil;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;

/**
 * @author chenzy
 * @since 2020-05-09
 *   TilePane
 */
public class TilePaneTest extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {

        var vp = new TilePane();

        vp.getChildren().addAll(FXUtil.getButtonList("1","1","1","1","1","1"));

        primaryStage.setScene(new Scene(vp));
        primaryStage.show();

    }
}
