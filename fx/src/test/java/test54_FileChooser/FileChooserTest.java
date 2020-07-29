package test54_FileChooser;

import com.czy.fx.myfx.MyFileChooser;
import com.czy.fx.myfx.Mybutton;
import com.czy.fx.test.FXUtil;
import com.czy.util.io.FileUtil;
import com.czy.util.list.ListUtil;
import javafx.application.Application;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.Optional;

/**
 * @author chenzy
 *
 * @since 2020-05-23
 */
public class FileChooserTest extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        var anchorPane = new AnchorPane();
        var box = new VBox(10);
        var fileLabel = new Label("文件:");
        var textArea = new TextArea();
        textArea.setWrapText(true);
        var defaultDir = "C:\\Users\\Samsung\\Desktop";
        box.getChildren().addAll(new Mybutton("单选文件").onClick(event -> {
            var file = new MyFileChooser("单选文件").fileFilter("图片类型", "*.jpg", "*.png").chooseFile();
            if (file != null) {
                fileLabel.setText("文件:" + file.getName());
                textArea.appendText(FileUtil.readFile(Optional.of(file)));
            }
        }), new Mybutton("保存文本").onClick(event -> {
            var file = new MyFileChooser(null, defaultDir).defaultFileName("a.text").fileFilter("文本类型", "*.text").saveFile();
            FileUtil.write(file, textArea.getText());
        }), new Mybutton("选择文件夹").onClick(event -> {
            var directoryChooser = new DirectoryChooser();
            File dirFile = directoryChooser.showDialog(new Stage());
            if (dirFile != null) {

            }
        }), new Mybutton("多选文件").onClick(event -> {
            Mybutton b = (Mybutton) event.getTarget();
            var fileList = new MyFileChooser(b.getText()).chooseFiles();
            if (ListUtil.isNotEmpty(fileList)) {
                fileList.forEach(file -> System.out.println(file.getName()));
            }
        }), fileLabel, textArea);
        anchorPane.getChildren().add(box);
        primaryStage.setTitle("文件选择器");
        FXUtil.setDefaultValue(primaryStage, anchorPane);
    }
}
