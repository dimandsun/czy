package com.czy.fx.test1_helloWorld;

import com.czy.util.StringUtil;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * @author chenzy
 * @since 2020-04-25
 * @description
 * 生命周期：init()、start()、stop()
 * 线程：start和stop中才右ui线程
 * Stage
 */
public class HelloWorld extends Application {


    @Override
    public void start(Stage stage) {
        stage.setTitle("Hello World!");
        /*加窗口图标*/
        stage.getIcons().add(getImag("草莓.jpg"));

//        stage.setIconified(true);//设置最小化
//        stage.setMaximized(true);//最小化
//        stage.close();//关闭窗口
//        stage.setResizable(false);//设置窗口不可改变大小
//        stage.setWidth(30);//设置宽度
//        stage.setHeight(50);//设置高度
//        stage.setMaxHeight(200);//设置最大高度
//        stage.setMaxWidth(100);//设置最大宽度
//        stage.setMaximized();
        //监听窗口宽度改变
        stage.widthProperty().addListener((observableValue, oldNumber, newNumber) -> {
            StringUtil.println("窗口宽度{}->{}",oldNumber,newNumber);
        });
        //监听窗口x轴位移
        stage.xProperty().addListener((observableValue, number, t1) -> {
            StringUtil.println("窗口x轴{}->{}",number,t1);
        });
        stage.setFullScreen(true);//全屏
        stage.setOpacity(0.5);//设置透明度，0是完全透明，1是完全不透明
        stage.setAlwaysOnTop(true);//窗口置顶
        Button btn = new Button();
        btn.setText("Say 'Hello World'");
        btn.setOnAction(event -> System.out.println("Hello World!"));
        Button mixBtn = new Button("最小化");
        mixBtn.setOnAction(e->stage.setIconified(true));
        StackPane root = new StackPane();
        root.getChildren().add(btn);
        root.getChildren().add(mixBtn);
        Scene scene = new Scene(root, 300, 250);
        stage.setScene(scene);
        stage.show();
//        Platform.exit();//程序退出
    }

    @Override
    public void stop() throws Exception {
        StringUtil.println("关闭");
    }

    public static void main(String[] args) {
        launch();
    }
    public void playMisc() {
        Media media = new Media("file:/D:/sound.wav");
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        mediaPlayer.play();
    }
    public Image getImag(String fileName){
        /*可以获取资源路径，但是打包成exe或者是jar时，会导致无法运行，因为jvm根本无法获取你的这个文件，后期打包时需要更改此方法*/
       String resourcePath = this.getClass().getResource("").getPath();
       /*获取out文件夹所在路径或.exe,.jar所在文件夹的路径*/
        String projectPath = System.getProperty("user.dir");
        return new Image("file:" +resourcePath+fileName);
    }
}

