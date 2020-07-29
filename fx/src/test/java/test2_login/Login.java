package test2_login;

import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * @author chenzy
 *
 * @since 2020-04-25
 */
public class Login extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("JavaFX Welcome");

        var grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);//行间隔
        grid.setVgap(10);//列间隔
        grid.setPadding(new Insets(25, 25, 25, 25));

        Text scenetitle = new Text("Welcome");
        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(scenetitle, 0, 0, 2, 1);
        //创建Label对象，放到第0列，第1行
        Label userName = new Label("test52_binding.User Name:");
        grid.add(userName, 0, 1);

        //创建文本输入框，放到第1列，第1行
        TextField userTextField = new TextField();
        userTextField.setTooltip(new Tooltip(" "));//鼠标悬停提示
        userTextField.setPromptText("背景提示：输入任意字符");//
        userTextField.setFocusTraversable(false);//取消焦点
        /*文本改变监听*/
        userTextField.textProperty().addListener((observableValue,  oldValue,  newValue)->{

        });
        /*文本被选中监听*/
        userTextField.selectedTextProperty().addListener((observableValue,  oldValue,  newValue)->{

        });


        grid.add(userTextField, 1, 1);
        {
            //特效
            var ff=new FadeTransition();
            ff.setDuration(Duration.seconds(0.5));
            ff.setNode(grid);
            ff.setFromValue(0);
            ff.setToValue(1);
            ff.play();
        }


        Label pw = new Label("Password:");
        grid.add(pw, 0, 2);

        PasswordField pwBox = new PasswordField();
        grid.add(pwBox, 1, 2);

        Button btn = new Button("Sign in");
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(btn);//将按钮控件作为子节点
        grid.add(hbBtn, 1, 4);//将HBox pane放到grid中的第1列，第4行
        final Text actiontarget=new Text();//增加用于显示信息的文本
        grid.add(actiontarget, 1, 6);
        btn.setOnAction(new EventHandler<ActionEvent>() {//注册事件handler
            @Override
            public void handle(ActionEvent e) {
                actiontarget.setFill(Color.FIREBRICK);//将文字颜色变成 firebrick red
                actiontarget.setText("Sign in button pressed");
            }
        });

        Scene scene = new Scene(grid, 300, 275);
        stage.setScene(scene);


        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
