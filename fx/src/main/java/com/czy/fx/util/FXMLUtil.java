package com.czy.fx.util;

import com.czy.fx.test.test68_FXML2.UserBuilder;
import com.czy.user.model.User;
import com.czy.util.FileUtil;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * @author chenzy
 * @since 2020-06-15
 */
public class FXMLUtil {
    private FXMLUtil() {
    }

    /**
     * 根据fxml文件加载FXMLLoader
     *
     * @param moduleDir   模块名，可以为空
     * @param resourePath fxml所在目录。相对目录
     * @return
     */
    public static FXMLLoader getLoader(String moduleDir, String resourePath) {
        try {
            var fxmlLoader = new FXMLLoader();
            var url = FileUtil.getResourceFile(moduleDir, resourePath).toURI().toURL();
            fxmlLoader.setLocation(url);
            return fxmlLoader;
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        }
    }
    public static<T> T getRoot(String moduleDir, String resourePath){
        var loader=getLoader(moduleDir,resourePath);
        try {
            return loader.load();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void setDefault(Stage stage, Parent root) {
        stage.setScene(new Scene(root));
        stage.show();
    }
}
