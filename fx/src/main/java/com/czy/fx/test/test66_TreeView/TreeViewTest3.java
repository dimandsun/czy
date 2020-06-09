package com.czy.fx.test.test66_TreeView;

import com.czy.fx.test.FXUtil;
import javafx.application.Application;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTreeCell;
import javafx.scene.control.cell.ChoiceBoxTreeCell;
import javafx.scene.control.cell.ComboBoxTreeCell;
import javafx.scene.control.cell.TextFieldTreeCell;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.StringConverter;

/**
 * @author chenzy
 * @since 2020-06-08
 */
public class TreeViewTest3 extends Application {
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
        treeView.setCellFactory(new Callback<TreeView<String>, TreeCell<String>>() {
            @Override
            public TreeCell<String> call(TreeView<String> param) {
                return new TreeCell<>(){
                    @Override
                    protected void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty||item==null){
                            setGraphic(null);
                            return;
                        }
                        var box =new HBox();
                        var label = new Label(item);
                        box.getChildren().add(label);
                        setGraphic(box);
                    }
                };
            }
        });
        anchorPane.getChildren().addAll(btn, treeView);
        FXUtil.setDefaultValue(primaryStage, anchorPane);
        AnchorPane.setTopAnchor(treeView, btn.getHeight());
    }
}
