package test75_HTMLEditor;

import com.czy.fx.test.FXUtil;
import javafx.application.Application;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.web.HTMLEditor;
import javafx.stage.Stage;

/**
 * @author chenzy
 * @since 2020-06-15
 */
public class HTMLEditorTest extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        var anchorPane = new AnchorPane();
        var btn = new Button("按钮1");
        var btn2 = new Button("按钮2");
        var htmlEditor = new HTMLEditor();

        btn.setOnAction(event -> {
            htmlEditor.getHtmlText();

        });

        htmlEditor.setPrefSize(700,400);
        htmlEditor.setHtmlText("abc");
        anchorPane.getChildren().addAll(btn,btn2,htmlEditor);
        FXUtil.setDefaultValue(primaryStage,anchorPane);
        AnchorPane.setLeftAnchor(btn2,btn.getWidth()+10);
        AnchorPane.setTopAnchor(htmlEditor,btn.getHeight()+10);
    }
}
