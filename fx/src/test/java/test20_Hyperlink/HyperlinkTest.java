package test20_Hyperlink;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * @author chenzy
 * @since 2020/5/10
 *  Hyperlink 超链接
 */
public class HyperlinkTest extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {

        var ap=new AnchorPane();
        /*link的Button也会触发link的点击事件*/
        var link=new Hyperlink("百度一下",new Button("按钮"));
        /*用默认浏览器打开指定网址*/
        link.setOnAction(event -> getHostServices().showDocument("www.baidu.com"));
        ap.getChildren().add(link);

        primaryStage.setScene(new Scene(ap));
        primaryStage.show();
    }
}
