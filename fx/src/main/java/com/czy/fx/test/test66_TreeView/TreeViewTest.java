package com.czy.fx.test.test66_TreeView;

import com.czy.fx.test.FXUtil;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTreeCell;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.StringConverter;

/**
 * @author chenzy
 * @since 2020-06-08
 */
public class TreeViewTest extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        var anchorPane = new AnchorPane();
        var treeView = new TreeView<String>();
        var treeRoot = new TreeItem<>("中国");
        var treePro = new TreeItem<>("江西");
        var treeCity = new TreeItem<>("吉安");
        var treeCity2 = new TreeItem<>("赣州");
        treePro.getChildren().addAll(treeCity, treeCity2);
        treeRoot.getChildren().addAll(treePro);
        treeView.setRoot(treeRoot);

        treeRoot.setExpanded(true);
        //不显示根节点
        treeView.setShowRoot(false);
        //判断是否有子节点
        treeCity.isLeaf();
        //获取同级的前面的节点
        treeCity2.previousSibling();
        treeCity2.previousSibling(treeCity2);
        //后面的节点
        treeCity2.nextSibling();
        //父节点
        treeCity2.getParent();
        //
        treeView.setFixedCellSize(20);
        //多选
        treeView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        treeView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            System.out.println("a:"+newValue);
        });
        treeView.getFocusModel().focus(0);
        treeView.requestFocus();
        //展开节点数
        treeView.getExpandedItemCount();
        var btn = new Button();
        //可编辑
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
        //在跟节点监听，事件会传递给子节点，监听节点值改变
        treeRoot.addEventHandler(TreeItem.valueChangedEvent(), (EventHandler<TreeItem.TreeModificationEvent<String>>) event -> {
            System.out.println("a:"+event.getNewValue());
            event.getTreeItem();
        });
        //监听节点图标改变
        treeRoot.addEventHandler(TreeItem.<String>graphicChangedEvent(), event -> System.out.println("图标改变:"+event.getNewValue()));
        //监听节点收起
        treeRoot.addEventHandler(TreeItem.<String>branchCollapsedEvent(), event -> System.out.println("收起" + event.getNewValue()));
        //监听节点展开
        treeRoot.addEventHandler(TreeItem.<String>branchExpandedEvent(), event -> System.out.println("展开" + event.getNewValue()));
        //监听节点-添加删除
        treeRoot.addEventHandler(TreeItem.<String>childrenModificationEvent(), event -> {
            System.out.println("添加/删除" + event.getNewValue());
            event.wasAdded();
            event.wasRemoved();
        });
        //监听节点所有类型操作
        treeRoot.addEventHandler(TreeItem.<String>treeNotificationEvent(), event -> {
            System.out.println("监听节点所有类型操作：" + event.getNewValue());
            event.wasAdded();
            event.wasRemoved();
            event.wasPermutated();//是否是排序操作
        });
//            treeCity.getChildren().sort();//排序
        anchorPane.getChildren().addAll(btn, treeView);
        FXUtil.setDefaultValue(primaryStage, anchorPane);
        AnchorPane.setTopAnchor(treeView, btn.getHeight());
    }
}
