package com.czy.fx.test.test65_TableView;

import com.czy.fx.test.FXUtil;
import com.czy.user.model.User;
import com.czy.util.ObjectUtil;
import javafx.application.Application;
import javafx.beans.InvalidationListener;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.ScheduledService;
import javafx.concurrent.Task;
import javafx.geometry.NodeOrientation;
import javafx.scene.control.*;
import javafx.scene.control.cell.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.Duration;
import javafx.util.StringConverter;

import java.util.Comparator;

/**
 * @author chenzy
 * @since 2020-06-05
 */
public class TableViewTest extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        var anchorPane = new AnchorPane();
        var userObservableList = FXCollections.observableArrayList(ObjectUtil.createList(User.class, "陈志源", "张月"));
        var user1 = userObservableList.get(0);
        var user2 = userObservableList.get(1);
        user1.setMobile("18720929479");
        user1.setAge(26);
        user2.setAge(20);
        user2.setDelete(true);
        user2.setScore(0.9);
        var tableView = new TableView<>(userObservableList);
        var nameColumn = new TableColumn<User, String>("姓名");
        var mobileColumn = new TableColumn<User, String>("电话");
        var ageColumn = new TableColumn<User, Number>("年龄");
        var bolColumn = new TableColumn<User, Boolean>("布尔");
        var scoreColumn = new TableColumn<User, Double>("积分");

        nameColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getName()));
        //参数mobile为bean的属性名
        mobileColumn.setCellValueFactory(new PropertyValueFactory<>("mobile"));
//        mobileColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getMobile()));
        scoreColumn.setCellValueFactory(new PropertyValueFactory<>("score"));
        ageColumn.setCellValueFactory(param -> new SimpleIntegerProperty(param.getValue().getAge()));
        bolColumn.setCellValueFactory(param -> new SimpleBooleanProperty(param.getValue().getDelete() == null ? true : param.getValue().getDelete() == null));
        //合并列
        var groupColumn = new TableColumn<User, Object>("信息");
        groupColumn.getColumns().addAll(nameColumn, mobileColumn);
        tableView.getColumns().addAll(groupColumn, ageColumn, bolColumn, scoreColumn);
        var btn = new Button("修改");
        btn.setOnAction(event -> {
//            user1.setAge(18);
//            tableView.refresh();
            //无效
            user1.setMobile("123");
        });

//        tableView.setPrefWidth();
//        tableView.setPlaceholder();//空数据时显示
        //列宽度
        nameColumn.setPrefWidth(100);
//        nameColumn.setVisible(false);//隐藏列
        //可以手动选择隐藏显示某列
        tableView.setTableMenuButtonVisible(true);
        //滚动到指定行
        tableView.scrollTo(0);

        //滚动到指定列
        tableView.scrollToColumn(ageColumn);
        //设值单元格尺寸
        tableView.setFixedCellSize(60);
        //设置是否可以编辑，还需给列设值工厂方法
        tableView.setEditable(true);
        //多选:可以选中多行
        tableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        tableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            System.out.println(newValue);
        });
        tableView.getSelectionModel().getSelectedIndices().forEach(index -> System.out.println(index));
        //可以选择单个单元格
        tableView.getSelectionModel().setCellSelectionEnabled(true);
        tableView.getSelectionModel().getSelectedCells().addListener((InvalidationListener) observable -> {
            var observableList = (ObservableList<TablePosition>) observable;
            observableList.forEach(tablePosition -> {
                //单元格数据
                var o = tablePosition.getTableColumn().getCellData(tablePosition.getRow());
                System.out.println(o);
            });
            System.out.println();
        });


//        tableView.getSelectionModel().selectPrevious();
        //焦点
//        tableView.getFocusModel().focus();
        //内容尺寸平均分布
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tableView.setColumnResizePolicy(new Callback<TableView.ResizeFeatures, Boolean>() {
            @Override
            public Boolean call(TableView.ResizeFeatures param) {
                if (param.getColumn() == null) {
                    return false;
                }
                param.getColumn().getPrefWidth();
                //无法拖动列宽度
                return true;
            }
        });
        //禁止列排序
        nameColumn.setSortable(false);
        //排序优先级：
        tableView.getSortOrder().addAll(nameColumn, ageColumn);
        //点击列头部的排序按钮时触发
        tableView.setSortPolicy(param -> {
            param.getColumns().forEach(userTableColumn -> {
                userTableColumn.getColumns();
                if (userTableColumn.getText().equals("年龄") && userTableColumn.getSortType() == TableColumn.SortType.ASCENDING) {
                    param.getItems().sort(Comparator.comparing(User::getName));
                    userTableColumn.setSortNode(new Label("升"));
                } else if (userTableColumn.getText().equals("年龄") && userTableColumn.getSortType() == TableColumn.SortType.DESCENDING) {
                    userTableColumn.setSortNode(new Label("将"));
                    param.getItems().sort((o1, o2) -> o2.getName().compareTo(o1.getName()));
                }
            });
            return true;
        });
        //
       /* nameColumn.setComparator(new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return 0;
            }
        });*/
        //
//       tableView.getItems().sort(Comparator.comparing(User::getName));
        //        列
        nameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        ageColumn.setCellFactory(TextFieldTableCell.forTableColumn(new StringConverter<Number>() {
            @Override
            public String toString(Number object) {
                return object.toString();
            }

            @Override
            public Number fromString(String string) {
                return Integer.valueOf(string);
            }
        }));

        //列的排列顺序
        tableView.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
        //滚动监听：
        tableView.setOnScrollTo(event -> {

        });
        //自定义单元格
        ageColumn.setCellFactory(ChoiceBoxTableCell.forTableColumn(new StringConverter<Number>() {
            @Override
            public String toString(Number object) {
                return object.toString();
            }

            @Override
            public Number fromString(String string) {
                return Integer.valueOf(string);
            }
        }, 10, 20, 30));
        bolColumn.setCellFactory(ComboBoxTableCell.forTableColumn(new StringConverter<Boolean>() {

            @Override
            public String toString(Boolean object) {
                return object.toString();
            }

            @Override
            public Boolean fromString(String string) {
                return Boolean.valueOf(string);
            }
        }, true, false));
        var scheduledService = new ScheduledService<Double>() {
            Double value=0d;
            @Override
            protected Task<Double> createTask() {
                return new Task<>() {
                    @Override
                    protected Double call() {
                        return value += 0.1;
                    }
                };
            }
        };
        scheduledService.setDelay(Duration.seconds(0));
        scheduledService.setPeriod(Duration.seconds(1));
        scheduledService.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue!=null){
                if (newValue>1){
                    scheduledService.cancel();
                }
                user2.setScore(newValue);
                tableView.refresh();
            }
        });

        var btn2 = new Button("分数变化");
        btn2.setOnAction(event -> {
            scheduledService.start();
        });
        //
        bolColumn.setCellFactory(CheckBoxTableCell.forTableColumn(bolColumn));
        scoreColumn.setCellFactory(ProgressBarTableCell.forTableColumn());
        mobileColumn.setCellFactory(new Callback<TableColumn<User, String>, TableCell<User, String>>() {
            @Override
            public TableCell<User, String> call(TableColumn<User, String> param) {
                return new TableCell<>(){
                    @Override
                    protected void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty||item==null){
                            return;
                        }
                        var box=new HBox();
                        var label = new Label(item);
//                        box.setAlignment(Pos.CENTER);
                        box.getChildren().add(label);
                        setGraphic(box);
                    }
                };
            }
        });
     /*   tableView.setRowFactory(new Callback<TableView<User>, TableRow<User>>() {
            @Override
            public TableRow<User> call(TableView<User> param) {
                return new TableRow<>(){
                    @Override
                    protected void updateItem(User item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty||item==null){
                            return;
                        }
                        setTooltip(new Tooltip(""));
                    }
                };
            }
        });*/
        anchorPane.getChildren().addAll(btn,btn2, tableView);
        FXUtil.setDefaultValue(primaryStage, anchorPane);
        AnchorPane.setLeftAnchor(btn2, btn.getWidth());
        AnchorPane.setTopAnchor(tableView, btn.getWidth());
    }
}
