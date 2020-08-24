package com.czy.question;

import com.czy.core.ProjectContainer;
import com.czy.core.model.ProjectInfo;
import com.czy.http.ApplicationContext;
import com.czy.http.Server;
import com.czy.log.LogFactory;
import com.czy.util.io.FileUtil;
import com.czy.util.model.SettingFile;


/**
 * @author chenzy
 * @since 2020/7/24
 */
public class Main {
    public static void main(String[] args) {
        /*加载业务代码容器*/
        ProjectInfo.init(FileUtil.getResourceFile("question_bank","application.yml"));
        ProjectContainer.getInstance().initProject();
        /*加载日志*/
        LogFactory.initLog(new SettingFile(null,"application-"+ProjectInfo.getInstance().getActive()+".yml"));
        /*初始化服务*/
        ApplicationContext.instance().load(new SettingFile(null,"application-"+ProjectInfo.getInstance().getActive()+".yml"));
        ApplicationContext.instance().addServlet("/default", "default", DispatchServlet.class);
        var server=new Server();
        /*开启服务*/
        server.start();
        /*程序主线程停止时执行*/
        server.stop(o -> {
            server.close();
            ProjectContainer.getInstance().close();
            LogFactory.close();
        });
        /*var questionController=(QuestionController)projectContainer.getBeanMap().get("questionController").getBean();
        var question=new Question();
        question.setName("adsfasd");
        var resultVO=questionController.insert(question);
        System.out.println(resultVO);*/
    }
}
