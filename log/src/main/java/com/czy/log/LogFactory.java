package com.czy.log;

import com.czy.util.io.FileUtil;
import com.czy.util.text.StringUtil;
import com.czy.util.time.TimeUtil;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
/**
 * @author chenzy
 * @date 2020-07-16
 */
public class LogFactory {
    private static HashMap<String,Log> logMap = (HashMap<String,Log>) Collections.synchronizedMap(new HashMap<String,Log>());
    /**
     * 默认获取名叫log的日志，日志信息写到当前磁盘/logs/log.log下
     * @return
     */
    public static Log getLog() {
        String logName = "log";
        if (logMap.containsKey(logName)){
            return logMap.get(logName);
        }
        var filePath=FileUtil.getRoot().get()+File.separator+"logs"+File.separator+"log.log";
        var log = createLog(logName,filePath,LogLevel.ALL,TimeUtil.yyyyMMddHHmmssSSS);
        logMap.put(logName,log);
        return logMap.get(logName);
    }
    public static Log getLog(String logName) {
        if (logMap.containsKey(logName)){
            return logMap.get(logName);
        }
        reloadLogs();
        var log= logMap.get(logName);
        if (log==null){
            //不写日志文件，只是控制台打印
            var logger=Logger.getLogger(logName);
            log =new Log(logger);
            logMap.put(logName,log);
        }
        return logMap.get(logName);
    }
    public static Log getLog(Class c) {
        return getLog(c.getSimpleName());
    }
    private static Log createLog(String logName, String filePath, LogLevel level, String datePattern) {
        var logger=Logger.getLogger(logName);
        FileHandler fileHandler = null;
        try {
            fileHandler = new FileHandler(filePath,true);
        } catch (IOException e) {
            e.printStackTrace();
        }
        var formatter = new LogFormatter(datePattern);
        logger.setLevel(level.getLevel());
        fileHandler.setFormatter(formatter);
        logger.addHandler(fileHandler);
        return new Log(logger);
    }
    public static void reloadLogs(){
       var file =FileUtil.getResourceFile("log.yml");
        FileUtil.readConfigFileByYML(file).map(map->(List<Map<String,Object>>)map.get("logs")).get().forEach(map->{
            String logName =map.get("logName").toString();
            if (logMap.containsKey(logName)){
                return;
            }
            String filePath= map.get("filePath").toString();
            if ('/'!=filePath.charAt(0)){
                filePath = FileUtil.getRoot().get()+filePath;
            }
            String datePattern= StringUtil.getStr(map.get("datePattern"), TimeUtil.yyyyMMddHHmmssSSS);
            LogLevel level= LogLevel.getLevel(StringUtil.getStr(map.get("level"), "all"));
            var log =createLog(logName,filePath,level,datePattern);
            logMap.put(logName,log);
        });
    }


}
