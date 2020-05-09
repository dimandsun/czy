package com.czy.fx.test.test14_GridPane;

import com.czy.fx.test.FXUtil;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Stage;

/**
 * @author chenzy
 * @since 2020-05-09
 * @description 网格布局 GridPane
 */
public class GridPaneTest extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {

        var gp = new GridPane();
        FXUtil.setColor(gp,"#FAF0E6");
        for (int i=4;i>=0;i--){
            for (int j=4;j>=0;j--){
                if (i==0){

                }else {
                    gp.add(new Button(i+","+j),i,j);
                }
            }
        }
        gp.add(new Button("0,6"),0,6);

        /*这三个方法不起效果，后续研究*/
        gp.setConstraints(new Button("哈哈"),0,0);
        gp.setColumnIndex(new Button("哈哈1"),0);
        gp.setRowIndex(new Button("aaa"),0);

        FXUtil.setStyle(gp,10d,null,10d,null,gp.getChildren().get(0),new Insets(10));

        /*设置第一行间距*/
        gp.getRowConstraints().add(0,new RowConstraints(50));

        /*设置第一列间距*/
        gp.getColumnConstraints().add(0,new ColumnConstraints(50));


        primaryStage.setScene(new Scene(gp));

        primaryStage.show();
    }
}
