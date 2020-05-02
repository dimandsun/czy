package com.czy.fx.test5_muliStage;

import javafx.application.Application;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * @author chenzy
 * @description
 * @since 2020/4/27
 */
public class MuliStage extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        Integer i =0;
        {
            Stage stage1 =new Stage();
            stage1.setTitle("s"+i++);
            stage1.initStyle(StageStyle.DECORATED);//默认
            stage1.show();
        }
        {
            Stage stage1 =new Stage();
            stage1.setTitle("s"+i++);
            stage1.initStyle(StageStyle.TRANSPARENT);//完全透明
            stage1.show();
        }
        {
            Stage stage1 =new Stage();
            stage1.setTitle("s"+i++);
            stage1.initStyle(StageStyle.UNDECORATED);//白色且不带装饰 效果和TRANSPARENT类似
            stage1.show();
        }
        {
            Stage stage1 =new Stage();
            stage1.setTitle("s"+i++);
            stage1.initStyle(StageStyle.UNIFIED);//即没有标题
            stage1.show();
        }
        {
            Stage stage1 =new Stage();
            stage1.setTitle("s"+i++);
            stage1.initStyle(StageStyle.UTILITY);//又上角只有一个关闭窗口按钮
            stage1.show();
        }
        {
            Stage stage1 =new Stage();
            stage1.setTitle("s"+i++);
            stage1.initStyle(StageStyle.UTILITY);//又上角只有一个关闭窗口按钮
            stage1.show();
        }
        {
            Stage stage1 =new Stage();
            stage1.setTitle("s"+i++);
            stage1.initModality(Modality.APPLICATION_MODAL);//没有关闭此窗口前，无法访问更底层窗口
            stage1.show();
        }
        {
            Stage stage1 =new Stage();
            stage1.setTitle("s"+i++);
            stage1.show();

            Stage stage2 =new Stage();
            stage2.setTitle("s"+i++);
            stage2.initOwner(stage1);
            stage2.initModality(Modality.WINDOW_MODAL);//没有关闭此窗口前，无法访问他的子窗口
            stage2.show();
        }
    }
}
