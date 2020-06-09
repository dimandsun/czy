package com.czy.fx.test.test66_TreeView;

import com.czy.fx.test.FXUtil;
import javafx.application.Application;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTreeCell;
import javafx.scene.control.cell.ChoiceBoxTreeCell;
import javafx.scene.control.cell.ComboBoxTreeCell;
import javafx.scene.control.cell.TextFieldTreeCell;
import javafx.scene.control.skin.VirtualFlow;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.StringConverter;

/**
 * @author chenzy
 * @since 2020-06-08
 */
public class TreeViewTest2 extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        var anchorPane = new AnchorPane();
        var treeView = new TreeView<String>();
        var root = new TreeItem<>("中国");

        var jx = new TreeItem<>("江西省");
        var ji = new TreeItem<>("吉安市");
        var gan = new TreeItem<>("赣州市");
        jx.getChildren().addAll(ji, gan);

        var gd = new TreeItem<>("广东省");
        var gz = new TreeItem<>("广州市");
        var fs = new TreeItem<>("佛山市");
        gd.getChildren().addAll(gz, fs);

        var hn = new TreeItem<>("湖南省");
        var cs = new TreeItem<>("长沙市");
        var cz = new TreeItem<>("郴州市");
        hn.getChildren().addAll(cs, cz);


        root.getChildren().addAll(jx, gd, hn);
        treeView.setRoot(root);

        root.setExpanded(true);
        jx.setExpanded(true);


        var btn = new Button();
        treeView.setEditable(true);
        treeView.setCellFactory(TextFieldTreeCell.forTreeView());
        treeView.setCellFactory(TextFieldTreeCell.forTreeView(new StringConverter<String>() {
            @Override
            public String toString(String object) {
                return object;
            }

            @Override
            public String fromString(String string) {
                return string;
            }
        }));
        treeView.setCellFactory(ComboBoxTreeCell.forTreeView("a", "b", "c"));

        treeView.setCellFactory(ChoiceBoxTreeCell.forTreeView("a", "b", "c"));

        treeView.setCellFactory(CheckBoxTreeCell.forTreeView(param -> {
            if (treeView.getTreeItemLevel(param) == 1) {
                return new SimpleBooleanProperty(true);
            }
            return null;
        }));
    /*    treeView.setCellFactory(CheckBoxTreeCell.forTreeView(param -> new SimpleBooleanProperty(true), new StringConverter<>() {
            @Override
            public String toString(TreeItem<String> object) {
                System.out.println("toString："+object);
                return object.getValue();
            }

            @Override
            public TreeItem<String> fromString(String string) {
                System.out.println("fromString:"+string);
                return new TreeItem<>(string);
            }
        }));*/
        treeView.setCellFactory(CheckBoxTreeCell.forTreeView(param -> {
            if (treeView.getTreeItemLevel(param) == 1) {
                return new SimpleBooleanProperty(true);
            }
            param.addEventHandler(EventType.ROOT,event -> {
                System.out.println(1234);
            });
            return new SimpleBooleanProperty(false);
        }));
        treeView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            CheckBoxTreeCell checkBoxTreeCell1 = (CheckBoxTreeCell) treeView.getCellFactory().call(treeView);
            SimpleBooleanProperty simpleBooleanProperty1 = (SimpleBooleanProperty) checkBoxTreeCell1.getSelectedStateCallback().call(newValue);
            System.out.println(simpleBooleanProperty1);
        });
        anchorPane.getChildren().addAll(btn, treeView);
        FXUtil.setDefaultValue(primaryStage, anchorPane);
        AnchorPane.setTopAnchor(treeView, btn.getHeight());
    }
}
