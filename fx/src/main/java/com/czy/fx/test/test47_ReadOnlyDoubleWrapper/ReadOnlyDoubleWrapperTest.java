package com.czy.fx.test.test47_ReadOnlyDoubleWrapper;

import com.czy.fx.test.FXUtil;
import javafx.application.Application;
import javafx.beans.property.ReadOnlyDoubleWrapper;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import static com.czy.util.text.StringUtil.println;

/**
 * @author chenzy
 * 
 * @since 2020-05-20
 */
public class ReadOnlyDoubleWrapperTest extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        var anchorPane=new AnchorPane();
        /*可读可写*/
        var readOnlyDoubleWrapper=new ReadOnlyDoubleWrapper(5);
        /*仅可读*/
        var readOnlyDoubleProperty=readOnlyDoubleWrapper.getReadOnlyProperty();
        readOnlyDoubleWrapper.set(1);
        System.out.println(readOnlyDoubleWrapper.get());
        System.out.println(readOnlyDoubleProperty.get());
        readOnlyDoubleWrapper.addListener((observable, oldValue, newValue) -> {
            println("{}->{}",oldValue,newValue);
        });
        FXUtil.setDefaultValue(primaryStage,anchorPane);
    }
}
