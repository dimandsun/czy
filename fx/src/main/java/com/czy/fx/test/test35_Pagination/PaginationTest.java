package com.czy.fx.test.test35_Pagination;

import com.czy.fx.test.FXUtil;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Pagination;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * @author chenzy
 * @description
 * @since 2020-05-15
 */
public class PaginationTest extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {

        var root = new Group();
        var page = new Pagination();

        /*总页数*/
        page.setPageCount(10);
        /*当前页数*/
        page.setCurrentPageIndex(1);
        page.getStyleClass().add(Pagination.STYLE_CLASS_BULLET);
        /*监听*/
        page.currentPageIndexProperty().addListener((observable, oldValue, newValue) -> {
            System.out.println(newValue);
        });


        page.setPageFactory(param -> {
            var p= new Label("第"+param+"页");
            FXUtil.setColor(p, Color.BURLYWOOD);
            return p;
        });
        root.getChildren().add(page);
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }
}
