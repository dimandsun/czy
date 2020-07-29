package test62_ListView;

import com.czy.fx.test.FXUtil;
import javafx.application.Application;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.cell.CheckBoxListCell;
import javafx.scene.control.cell.ChoiceBoxListCell;
import javafx.scene.control.cell.ComboBoxListCell;
import javafx.scene.control.cell.TextFieldListCell;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.StringConverter;

/**
 * @author chenzy
 * @since 2020-06-01
 */
public class ListViewTestStringList extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        var anchorPane = new AnchorPane();
        var dataArr = new String[]{"czy","123","陈志源"};
        var observableList = FXCollections.observableArrayList(dataArr);
        var stringListView = new ListView<>(observableList);
        stringListView.setPlaceholder(new Label("没有数据"));
        anchorPane.getChildren().addAll(stringListView);
        //子项尺寸
        stringListView.setFixedCellSize(50);

        //选中第一个
        stringListView.getSelectionModel().select(0);
        //获取焦点
        stringListView.requestFocus();
        //阻止获得焦点，默认true
//        stringListView.setFocusTraversable(false);
        //焦点到指定子项
        stringListView.getFocusModel().focus(1);

        //多选：默认单选
        stringListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        // 选中多个
        stringListView.getSelectionModel().selectIndices(0,1, 2);
        //选中事件
        stringListView.getSelectionModel().selectionModeProperty().addListener((observable, oldValue, newValue) -> {
            System.out.println("选");
        });
        //重新加载数据
        stringListView.refresh();
        //出现滚动条时，滚到指定位置
//        userListView.scrollTo();
//        userListView.onScrollToProperty().addListener();
        //可编辑
        stringListView.setEditable(true);
        stringListView.setCellFactory(TextFieldListCell.forListView());
        stringListView.setCellFactory(TextFieldListCell.forListView(new StringConverter<String>() {
            @Override
            public String toString(String object) {
                return object+"-toString";
            }
            @Override
            public String fromString(String string) {
                return string+"-fromString";
            }
        }));
        /*子项加下拉框。可变参数为下拉框列表*/
        stringListView.setCellFactory(ComboBoxListCell.forListView(new StringConverter<String>() {
            @Override
            public String toString(String object) {
                return object+"-toString";
            }
            @Override
            public String fromString(String string) {
                return string+"-fromString";
            }
        },"1","2","3"));
        stringListView.setCellFactory(ChoiceBoxListCell.forListView(new StringConverter<String>() {
            @Override
            public String toString(String object) {
                return object+"-toString";
            }
            @Override
            public String fromString(String string) {
                return string+"-fromString";
            }
        },"1","2","3"));
        stringListView.setCellFactory(CheckBoxListCell.forListView(new Callback<String, ObservableValue<Boolean>>() {
            @Override
            public ObservableValue<Boolean> call(String param) {
                return new SimpleBooleanProperty(true);
            }
        }, new StringConverter<String>() {
            @Override
            public String toString(String object) {
                return object+"-toString1";
            }

            @Override
            public String fromString(String string) {
                return string+"-fromString1";
            }
        }));
        //
        stringListView.setOnEditStart(event -> {
            System.out.println("开始编辑");
        });
        stringListView.setOnEditCancel(event -> {
            System.out.println("取消编辑");
        });
        stringListView.setOnEditCommit(event -> {
            event.getNewValue();
            System.out.println("编辑完成");
        });
        stringListView.editingIndexProperty().addListener((observable, oldValue, newValue) -> {
            System.out.println("editingIndexProperty:Listener");
        });
        FXUtil.setDefaultValue(primaryStage, anchorPane);
    }
}
