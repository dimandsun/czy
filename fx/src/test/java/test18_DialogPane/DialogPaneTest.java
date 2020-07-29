package test18_DialogPane;

import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.List;

/**
 * @author chenzy
 * 
 * @since 2020-05-09
 */
public class DialogPaneTest extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {


        var btn = new Button("弹出对话框");
        btn.setOnAction(event -> {
            var dp = new DialogPane();
            dp.setHeaderText("标题");
            dp.setContentText("内容");
            dp.getButtonTypes().add(ButtonType.APPLY);
            dp.getButtonTypes().add(ButtonType.CANCEL);
            dp.getButtonTypes().add(ButtonType.CLOSE);
            dp.setGraphic(new ImageView("qq.jpg"));

            dp.setExpandableContent(new TextField("扩展信息"));
            /*初始化时扩展信息关闭*/
            dp.setExpanded(false);
            var stage=new Stage();
            stage.setScene(new Scene(dp));
            stage.initOwner(primaryStage);
            stage.initModality(Modality.WINDOW_MODAL);
            stage.setTitle("弹出的对话框");
            stage.show();

            Button close= (Button) dp.lookupButton(ButtonType.CLOSE);
            close.setOnAction(event1 -> {
                List<Node> a=dp.getChildren();
                stage.close();
            });

        });
        var ap = new AnchorPane();
        ap.getChildren().add(btn);
        primaryStage.setScene(new Scene(ap));
        primaryStage.show();
    }
}
