package test45_PropertyChangeSupport;

import test52_binding.User;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.beans.PropertyChangeSupport;

import static com.czy.util.text.StringUtil.println;

/**
 * @author chenzy
 * 
 * @since 2020-05-20
 */
public class PropertyChangeSupportTest extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        var anchorPane=new AnchorPane();

        var user = new User();
        var propertyChangeSupport=new PropertyChangeSupport(user);
        setName(propertyChangeSupport,user,"czy");

        propertyChangeSupport.addPropertyChangeListener(evt -> {
            var temp= (User) evt.getSource();
           println("{}:{}——>{}",evt.getPropertyName(),evt.getOldValue(),evt.getNewValue());
        });
        var nameText=new TextField();
        var btn = new Button("修改用户名");
        anchorPane.getChildren().addAll(nameText,btn);
        btn.setOnAction(event -> {
            setName(propertyChangeSupport,user,nameText.getText());
        });
        primaryStage.setScene(new Scene(anchorPane));
        primaryStage.setHeight(800);
        primaryStage.setWidth(1000);
        primaryStage.show();
        AnchorPane.setTopAnchor(btn,nameText.getHeight());

    }
    private void setName(PropertyChangeSupport propertyChangeSupport,User user,String name){
        propertyChangeSupport.firePropertyChange("userName",user.getName(),name);
        user.setName(name);
    }
}
