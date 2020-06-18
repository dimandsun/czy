package com.czy.car_server;

import com.czy.core.LogInfo;
import com.czy.core.ProjectContainer;
import com.czy.util.json.JsonUtil;
import com.czy.util.model.ResultVO;

import java.io.IOException;
import java.net.ServerSocket;

import static com.czy.util.tcp.SocketUtil.readData;
import static com.czy.util.tcp.SocketUtil.sendData;

/**
 * @author chenzy
 * @since 2020-06-17
 * 汽车管理系统服务端入口
 */
public class ServerMain {
    public static void startServer(CarServerInfo serverInfo) {
        try  {
            var serverSocket = new ServerSocket(serverInfo.getPort());
            while (true){
                var socket = serverSocket.accept();
                //读取客户端请求
                String questData = readData(socket);
                //处理请求
                String responseData = JsonUtil.model2Str(new ResultVO<>("asdfafd"));
                //向客户端发送响应
                sendData(socket,responseData);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        //加载日志框架
        var projectInfo = new CarServerInfo().init("car_server");
        LogInfo.initLog(projectInfo);
        //初始化框架
        var projectContainer = ProjectContainer.getInstance();
        projectContainer.addProjectInfo(projectInfo).initProject();
        //设置默认的数据源
        projectContainer.setDefaultDataSourceKey(projectInfo);
        //开始监听请求
        startServer(projectInfo);
    }
}
