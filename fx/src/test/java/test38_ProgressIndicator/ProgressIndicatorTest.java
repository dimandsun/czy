package test38_ProgressIndicator;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * @author chenzy
 * @since 2020/5/16
 *  进度图：进度条与进度圆
 */
public class ProgressIndicatorTest extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        var ap=new AnchorPane();
        var pi=new ProgressIndicator();
//        pi.setProgress(ProgressBar.INDETERMINATE_PROGRESS);
        pi.setProgress(0.5);

        pi.progressProperty().addListener((observable, oldValue, newValue) -> {
            System.out.println(newValue);
        });
        ap.getChildren().addAll(pi);

        primaryStage.setScene(new Scene(ap));
        primaryStage.show();
    }
}
