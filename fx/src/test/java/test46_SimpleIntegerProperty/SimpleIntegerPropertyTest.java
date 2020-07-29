package test46_SimpleIntegerProperty;

import javafx.application.Application;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import static com.czy.fx.test.FXUtil.setDefaultValue;
import static com.czy.util.text.StringUtil.println;

/**
 * @author chenzy
 *
 * @since 2020-05-20
 */
public class SimpleIntegerPropertyTest extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        var anchorPane = new AnchorPane();
        //SimpleIntegerProperty
        {
//            SimpleStringProperty类似
            var simpleIntegerProperty = new SimpleIntegerProperty(0);
            var btn = new Button();
            btn.setOnAction(event -> {
                simpleIntegerProperty.set(simpleIntegerProperty.get() + 1);
                Integer count = simpleIntegerProperty.get();
                btn.setText("点击" + count + "次");
            });
            simpleIntegerProperty.addListener((observable, oldValue, newValue) -> {
                println("{}->{}", oldValue, newValue);
            });
            anchorPane.getChildren().addAll(btn);
        }
        setDefaultValue(primaryStage, anchorPane);
    }

}