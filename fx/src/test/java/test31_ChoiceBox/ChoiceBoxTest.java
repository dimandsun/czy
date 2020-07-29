package test31_ChoiceBox;

import test52_binding.User;
import com.czy.util.ObjectUtil;
import com.czy.util.text.StringUtil;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.StringConverter;

/**
 * @author chenzy
 *  下拉列表
 * @since 2020-05-12
 */
public class ChoiceBoxTest extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        var ap = new AnchorPane();
        var cb = new ChoiceBox<User>();
        var userList = ObjectUtil.createList(User.class, "czy", "张三", "李四");
        cb.getItems().addAll(userList);

        /**/
      /*  test52_binding.User[] a = new test52_binding.User[]{new test52_binding.User("123")};
        ObservableList<test52_binding.User> list = FXCollections.observableArrayList();
        list.add(new test52_binding.User("ahdsfjk"));
        list = FXCollections.observableArrayList(a);
        cb.setItems(list);*/


        cb.getSelectionModel().selectedItemProperty().addListener((observableValue, s, user) -> {
            System.out.println(user);
        });
        cb.setConverter(new StringConverter<>() {
            @Override
            public String toString(User user) {
                return user.getName();
            }

            /*此方法不会执行*/
            @Override
            public User fromString(String s) {
                return null;
            }
        });
        /*展开列表*/
        cb.show();
        //移除选中
        cb.getSelectionModel().clearSelection();
        cb.getSelectionModel().selectedItemProperty().addListener((observableValue, oldUser, newUser) -> {
            StringUtil.println("内容改变:旧值：{}，新值：{}",oldUser,newUser);
        });
        var btn = new Button("切换");
        btn.setOnAction(event -> {
            String name = "李四";
            userList.forEach(user -> {
                if (user.getName().equals(name)) {
                    cb.setValue(user);
                    return;
                }
            });
        });

        ap.getChildren().addAll(btn, cb);
        AnchorPane.setBottomAnchor(cb, 10d);
        stage.setScene(new Scene(ap));
        stage.setHeight(900);
        stage.show();


    }
}
