package test61_Clipboard;

import com.czy.fx.test.FXUtil;
import javafx.application.Application;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.WritableImage;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;


/**
 * @author chenzy
 * @since 2020-05-28
 */
public class ClipboardTest2 extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        var clipboard = Clipboard.getSystemClipboard();

//        clipboard.clear();

        var anchorPane = new AnchorPane();
        var label = new Label("文本额啊");
        var text = new TextField();
        label.setOnDragDetected(event -> {
            var dragboard=label.startDragAndDrop(TransferMode.MOVE);
            var text1=new Text(label.getText());
            var image=new WritableImage((int)label.getWidth(),(int)label.getHeight());
            text1.snapshot(new SnapshotParameters(),image);
            dragboard.setDragView(image,10,10);


            var content=new ClipboardContent();
            content.putString(label.getText());
            dragboard.setContent(content);
        });
        text.setOnDragOver(event -> {
//            event.getAcceptedTransferMode()
            event.acceptTransferModes(TransferMode.MOVE);
        });
        text.setOnDragDropped(event -> {
            text.setText(event.getDragboard().getString());
            //拖拽完成
            event.setDropCompleted(true);
        });
        label.setOnDragDone(event -> {
            if (event.getTransferMode()==TransferMode.MOVE){
                label.setText("");
            }
        });

        anchorPane.getChildren().addAll(label,text);

        FXUtil.setDefaultValue(primaryStage, anchorPane);
        AnchorPane.setLeftAnchor(text,label.getWidth()+10);



        var scene = primaryStage.getScene();

    }
}
