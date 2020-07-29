import com.czy.fx.myfx.MyPasswordField;
import com.czy.fx.myfx.MyTextField;
import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 * @author chenzy
 * 
 * @since 2020-05-09
 */
public class LoginTest extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        var gp = new GridPane();

        Node[][] nodeArray = {{new Label("帐号："),new MyTextField()}
                ,{new Label("密码："),new MyPasswordField()}
        };

        primaryStage.setScene(new Scene(gp));
        primaryStage.show();
    }
}
