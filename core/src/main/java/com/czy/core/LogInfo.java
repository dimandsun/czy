package com.czy.core;

import com.czy.core.model.ProjectInfo;
import com.czy.util.FileUtil;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.LogManager;

import java.io.File;

/**
 * @author chenzy
 * @description
 * @since 2020-05-11
 */
public class LogInfo {
    public static void initLog(ProjectInfo projectInfo) {
        System.out.println("------------------------------日志正在初始化------------------------------");
        File file = FileUtil.getResourceFile(projectInfo.getModuleDir(),"log4j2-" + projectInfo.getActive().getMsg() + ".xml");
        LoggerContext context = (LoggerContext) LogManager.getContext(false);
        context.setConfigLocation(file.toURI());
        //重新初始化Log4j2的配置上下文
        context.reconfigure();
        System.out.println("------------------------------日志初始化完成------------------------------");



    }
}
