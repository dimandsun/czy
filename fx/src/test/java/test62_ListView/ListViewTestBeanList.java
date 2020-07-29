package test62_ListView;

import test52_binding.User;
import com.czy.fx.test.FXUtil;
import com.czy.util.ObjectUtil;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Callback;

/**
 * @author chenzy
 * @since 2020-06-01
 */
public class ListViewTestBeanList extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        var anchorPane = new AnchorPane();
        var userList = ObjectUtil.createList(User.class, "czy", "cc","asf");
        var userObservableList = FXCollections.observableArrayList(userList);
        var userListView = new ListView<>(userObservableList);
        userListView.setPlaceholder(new Label("没有数据"));
        anchorPane.getChildren().addAll(userListView);


        //选中第一个
        userListView.getSelectionModel().select(0);
        //获取焦点
        userListView.requestFocus();
        //多选：默认单选
        userListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        // 选中多个
        userListView.getSelectionModel().selectIndices(0,1, 2);
        //选中事件
        userListView.getSelectionModel().selectionModeProperty().addListener((observable, oldValue, newValue) -> {
            System.out.println("选");
        });
        //出现滚动条时，滚到指定位置
//        userListView.scrollTo();
//        userListView.onScrollToProperty().addListener();
        //可编辑,beanlist无效
        userListView.setEditable(true);

        userListView.setCellFactory(new Callback<ListView<User>, ListCell<User>>() {
            @Override
            public ListCell<User> call(ListView<User> param) {
                var listCell = new ListCell<User>() {
                    @Override
                    protected void updateItem(User user, boolean empty) {
                        super.updateItem(user, empty);
                        FXUtil.setColor(this, "#ff82ab");
                        if (!empty){
                            var box=new HBox(5);
                            var name = new Label(user.getName());
                            var mobile=new Label(user.getMobile());
                            box.getChildren().addAll(name,mobile);
                            this.setGraphic(box);
                        }
                    }
                };
                return listCell;
            }
        });
        FXUtil.setDefaultValue(primaryStage, anchorPane);
    }
}
