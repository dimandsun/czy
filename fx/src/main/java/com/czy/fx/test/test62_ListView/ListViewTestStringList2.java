package com.czy.fx.test.test62_ListView;

import com.czy.fx.test.FXUtil;
import javafx.application.Application;
import javafx.beans.Observable;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.Serializable;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author chenzy
 * @since 2020-06-04
 */
public class ListViewTestStringList2 extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        var anchorPane = new AnchorPane();
        var observableList = FXCollections.observableArrayList(new Callback<Data, Observable[]>() {
            @Override
            public Observable[] call(Data param) {
                System.out.println("call:" + param);
//                return new Observable[]{param.getName()};
                return new Observable[]{param.getName()};
            }
        });
        var a = new Data("12");
        var b = new Data("da");
        var c = new Data("c");
        observableList.addAll(a, b, c);
        var listView = new ListView<>(observableList);
        listView.setPlaceholder(new Label("没有数据"));
        listView.setEditable(true);
        var defaultFont = Font.font(15d);
        var daFont = Font.font(30d);
        AtomicReference<Integer> selectIndex = new AtomicReference<>();
        AtomicReference<Data> selectData = new AtomicReference<>();
        listView.setCellFactory(new Callback<ListView<Data>, ListCell<Data>>() {
            AtomicReference<Data> item1 = new AtomicReference<>();

            @Override
            public ListCell<Data> call(ListView<Data> param) {
                param.setOnEditStart(event -> {
                    item1.set(param.getItems().get(event.getIndex()));
                    System.out.println("setOnEditStart");
                });
                var listCell = new ListCell<Data>() {
                    @Override
                    public void commitEdit(Data newValue) {
                        super.commitEdit(newValue);
                        System.out.println("commitEdit");
                        var box = new HBox();
                        box.getChildren().add(new ImageView("qq.jpg"));
                        var label = new Label(newValue.getName().get());
                        box.getChildren().add(label);
                        this.setGraphic(box);

                    }

                    @Override
                    public void updateSelected(boolean selected) {
                        super.updateSelected(selected);
                    }

                    @Override
                    protected boolean isItemChanged(Data oldItem, Data newItem) {
                        return super.isItemChanged(oldItem, newItem);
                    }

                    @Override
                    public void cancelEdit() {
                        super.cancelEdit();
                        var box = new HBox();
                        box.getChildren().add(new ImageView("qq.jpg"));
                        box.getChildren().add(new Label(item1.get().getName().get()));
                        this.setGraphic(box);
                    }

                    @Override
                    public void startEdit() {
                        super.startEdit();
                        //listView可编辑时，点击会调用此方法
                        System.out.println("startEdit");
                        var box = new HBox();
                        box.getChildren().add(new ImageView("qq.jpg"));
                        var textField = new TextField(item1.get().getName().get());
                        box.getChildren().add(textField);
                        this.setGraphic(box);
                        textField.setOnKeyPressed(event -> {
                            if (event.getCode().equals(KeyCode.ENTER)) {
                                item1.get().setName(textField.getText());
                                this.commitEdit(item1.get());
                            }
                        });
                    }

                    @Override
                    protected void updateItem(Data item, boolean empty) {
                        super.updateItem(item, empty);
                        if (!empty) {
                            var box = new HBox();
                            box.getChildren().add(new ImageView("qq.jpg"));
                            box.getChildren().add(new Label(item.getName().get()));
                            this.setGraphic(box);
                        }
                    }
                };
                //鼠标悬停时
                listCell.hoverProperty().addListener((observable, oldValue, newValue) -> {
                    if (listCell.getGraphic() == null) {
                        return;
                    }
                    var box = (HBox) listCell.getGraphic();
                    var label = (Label) box.getChildren().get(1);
                    if (newValue) {
                        label.setFont(daFont);
                    } else {
                        label.setFont(defaultFont);
                    }
                });
                listCell.setOnDragDetected(event -> {
                    if (listCell.getGraphic() == null) {
                        return;
                    }
                    var dragboard = listCell.startDragAndDrop(TransferMode.MOVE);
                    var clipboardContent = new ClipboardContent();
                    clipboardContent.putString(param.getItems().indexOf(listCell.getItem()) + "");
                    dragboard.setContent(clipboardContent);
                });
                listCell.setOnDragEntered(event -> {
                    var target = (ListCell) event.getTarget();
                    var source = (ListCell) event.getSource();
                    var targetItem = target.getItem();
                    var sourceItem = source.getItem();
                    if (targetItem == null || sourceItem == null) {
                        return;
                    }
                    var tagetIndex = param.getItems().indexOf(targetItem);
                    var dragboard = event.getDragboard();
                    param.getFocusModel().focus(tagetIndex);
                });
                listCell.setOnDragOver(event -> {
                    event.acceptTransferModes(TransferMode.MOVE);
                });
                listCell.setOnDragDropped(event -> {
                    System.out.println("setOnDragDropped");
                    Integer sourceIndex = Integer.valueOf(event.getDragboard().getString());
                    var target = (ListCell) event.getGestureTarget();
                    var targetItem = target.getItem();
                    var sourceItem = param.getItems().get(sourceIndex);
                    if (targetItem == null||sourceItem==null) {
                        return;
                    }
                    var tagetIndex = param.getItems().indexOf(targetItem);

//                    var b1 =  observableList.remove(sourceIndex);
//                    var b1 = param.getItems().remove(sourceIndex);
//                    param.getItems().add(tagetIndex, sourceItem);
//                    System.out.println(b1);
                });
                return listCell;
            }
        });
        listView.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            selectIndex.set(newValue.intValue());
        });
        listView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            selectData.set(newValue);
        });

        var btn = new Button("修改");
        btn.setOnAction(event -> {
            a.setName("12123312");
        });
        var btn2 = new Button("刷新");
        btn2.setOnAction(event -> {
            listView.refresh();
        });
        anchorPane.getChildren().addAll(listView, btn, btn2);
        FXUtil.setDefaultValue(primaryStage, anchorPane);
        AnchorPane.setLeftAnchor(btn, 250d);
        AnchorPane.setLeftAnchor(btn2, 250d + btn.getWidth());
    }

    class Data implements Serializable {
        public Data(String name) {
            setName(name);
        }

        @Override
        public String toString() {
            return name.get();
        }

        private SimpleStringProperty name = new SimpleStringProperty();

        public SimpleStringProperty getName() {
            return name;
        }

        public void setName(String name) {
            this.name.set(name);
        }
    }
}

