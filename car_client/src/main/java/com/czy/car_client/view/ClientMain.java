package com.czy.car_client.view;

import com.czy.car_client.CarClientInfo;
import com.czy.fx.util.FXMLUtil;
import javafx.application.Application;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;


/**
 * @author chenzy
 * @since 2020-06-15
 * 客户端入口
 */
public class ClientMain extends Application {
    @Override
    public void start(Stage primaryStage) {
        GridPane gridPane = FXMLUtil.getRoot("car_client", "com/czy/car_client/view/user/login.fxml");
        FXMLUtil.setDefault(primaryStage, gridPane);
        primaryStage.setTitle("共享汽车管理系统");
    }

    @Override
    public void stop() throws Exception {
        //关闭通信管道
        var socket=CarClientInfo.getInstance().getSocket();
        if (socket!=null&&!socket.isClosed()){
            socket.shutdownInput();
            socket.shutdownOutput();
            socket.close();
        }
        super.stop();
    }

    public static void main(String[] args) {
        //创建服务端通信管道
//        CarClientInfo.getInstance().init("car_client").createSocket();
        //渲染页面
//        ClientMain.launch(args);
    }
}
