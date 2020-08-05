package test71_ResourceBundle;

import com.czy.fx.test.FXUtil;
import com.czy.util.io.FileUtilOld;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * @author chenzy
 * @since 2020-06-12
 */
public class ResourceBundleTest extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        var locale = Locale.getDefault();
        locale.getLanguage();//语言 zh
        locale.getCountry();//国家 CN US
        Locale.setDefault(new Locale("en","us"));
        var fxmlLoader=new FXMLLoader();
        var file = FileUtilOld.getCodeFile("fx","test71_ResourceBundle.ResourceBundle.fxml");
//        var resourceBundle= ResourceBundle.getBundle("test71_ResourceBundle.language");
        var  resourceBundle= ResourceBundle.getBundle("test71_ResourceBundle.language");
        fxmlLoader.setResources(resourceBundle);
        AnchorPane anchorPane =  fxmlLoader.load(new FileInputStream(file));
        FXUtil.setDefaultValue(primaryStage, anchorPane);
        ResourceBundleController controller = fxmlLoader.getController();
        var btn2 = controller.getBtn2();
        AnchorPane.setTopAnchor(btn2,controller.getBtn().getHeight());
        AnchorPane.setTopAnchor(controller.getBtn3(),btn2.prefHeight(-1)+btn2.getHeight());

    }
}
