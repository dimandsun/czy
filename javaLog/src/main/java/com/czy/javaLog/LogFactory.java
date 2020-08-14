package com.czy.javaLog;

import com.czy.util.io.FileUtil;
import com.czy.util.model.SettingFile;
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
@Deprecated
public class LogFactory {
    private static Map<String,Log> logMap = Collections.synchronizedMap(new HashMap<>());
    private static SettingFile settingFile;
    /**
     * 默认获取名叫log的日志，日志信息写到当前磁盘/logs/log.log下
     *
     * @return
     */
    public static Log getLog() {
        String logName = "log";
        if (logMap.containsKey(logName)) {
            return logMap.get(logName);
        }
        var filePath = FileUtil.getRoot() + "logs" + File.separator + "log％g.log";
        var log = createLog(new FileSetting(filePath, 1024, 30), logName, LogLevel.ALL, TimeUtil.yyyyMMddHHmmssSSS);
        logMap.put(logName, log);
        return logMap.get(logName);
    }

    public static Log getLog(String logName) {
        if (logMap.containsKey(logName)) {
            return logMap.get(logName);
        }
        reloadLogs();
        var log = logMap.get(logName);
        if (log == null) {
            //不写日志文件，只是控制台打印
            var logger = Logger.getLogger(logName);
            log = new Log(logger);
            logMap.put(logName, log);
        }
        return logMap.get(logName);
    }
    public static Log getLog(Class c) {
        return getLog(c.getSimpleName());
    }

    private static Log createLog(FileSetting fileSetting, String logName, LogLevel level, String datePattern) {
        var logger = Logger.getLogger(logName);
        FileHandler fileHandler = null;
        try {
            FileUtil.createFile(new File(fileSetting.filePath()));
            fileHandler = new FileHandler(fileSetting.filePath(), fileSetting.fileSize(), fileSetting.fileCount(), true);
        } catch (IOException e) {
            e.printStackTrace();
        }
        var formatter = new LogFormatter(datePattern);
        logger.setLevel(level.getLevel());
        fileHandler.setFormatter(formatter);
        logger.addHandler(fileHandler);
        return new Log(logger);
    }
    private static void reloadLogs() {
        if (settingFile==null){
            settingFile=new SettingFile("","log.yml");
        }
        var file = FileUtil.getResourceFile(settingFile.moduleDir(),settingFile.fileName());
        FileUtil.readYML(file).map(map -> (List<Map<String, Object>>) map.get("logs")).get().forEach(map -> {
            String logName = map.get("logName").toString();
            if (logMap.containsKey(logName)) {
                return;
            }
            String filePath = map.get("filePath").toString();
            if ('/' == filePath.charAt(0)) {
                filePath=filePath.substring(1);
            }
            filePath = FileUtil.getRoot() + filePath;
            Integer fileSize = StringUtil.getInt(map.get("fileSize"), 1024);
            Integer fileCount = StringUtil.getInt(map.get("fileCount"), 30);
            var fileSetting = new FileSetting(filePath, fileSize, fileCount);
            String datePattern = StringUtil.getStr(map.get("datePattern"), TimeUtil.yyyyMMddHHmmssSSS);
            LogLevel level = LogLevel.getLevel(StringUtil.getStr(map.get("level"), "all"));
            var log = createLog(fileSetting, logName, level, datePattern);
            logMap.put(logName, log);
        });
    }
    public static void reloadLogs(String moduleDir,String fileName) {
        settingFile=new SettingFile(moduleDir,fileName);
        reloadLogs();
    }


}
