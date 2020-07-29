package test56_Image;

import com.czy.fx.test.FXUtil;
import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;

/**
 * @author chenzy
 * 
 * @since 2020-05-23
 */
public class ImageTest extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        var anchorPane = new AnchorPane();
        String filePath = "E:\\work\\project\\czy\\czyr\\fx\\src\\main\\resource\\qq.jpg";
        var fileInputStream=new FileInputStream(new File(filePath));
        var img = new Image(fileInputStream,600,600,true,true);
        var img2 = new Image("file:"+filePath,600,600,true,true,true);
        var img3 = new Image("qq.jpg",600,600,true,true,true);
        var imgView = new ImageView(img3);
        anchorPane.getChildren().addAll(imgView);
        img.errorProperty().addListener((observable, oldValue, newValue) -> {

        });
        img.exceptionProperty().addListener((observable, oldValue, newValue) -> {

        });
        img.progressProperty().addListener((observable, oldValue, newValue) -> {

        });

        FXUtil.setDefaultValue(primaryStage,anchorPane);
    }
}
