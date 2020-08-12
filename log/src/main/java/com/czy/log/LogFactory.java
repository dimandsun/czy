package com.czy.log;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

/**
 * @author chenzy
 * @date 2020-08-06
 */
public class LogFactory {
    private LogFactory() {
    }
    private static Map<String, Log> logMap = Collections.synchronizedMap(new HashMap<>());
    public static Log getLog(String logName) {
        if (logMap.containsKey(logName)) {
            return logMap.get(logName);
        }
        reloadLogs();
        var log = logMap.get(logName);
        if (log == null) {
            //不写日志文件，只是控制台打印
            var logger = Logger.getLogger(logName);
            log = new Log();
            logMap.put(logName, log);
        }
        return logMap.get(logName);
    }
    public static Log getLog(Class c) {
        return getLog(c.getSimpleName());
    }
    private static void reloadLogs() {
       /* if (settingFile==null){
            settingFile=new SettingFile("","log.yml");
        }
        var file = FileUtil.getResourceFile(settingFile.moduleDir(),settingFile.fileName());
        FileUtil.readConfigFileByYML(file).map(map -> (List<Map<String, Object>>) map.get("logs")).get().forEach(map -> {
            String logName = map.get("logName").toString();
            if (logMap.containsKey(logName)) {
                return;
            }
            String filePath = map.get("filePath").toString();
            if ('/' == filePath.charAt(0)) {
                filePath = FileUtil.getRoot().get() + filePath;
            }
            Integer fileSize = StringUtil.getInt(map.get("fileSize"), 1024);
            Integer fileCount = StringUtil.getInt(map.get("fileCount"), 30);
            var fileSetting = new FileSetting(filePath, fileSize, fileCount);
            String datePattern = StringUtil.getStr(map.get("datePattern"), TimeUtil.yyyyMMddHHmmssSSS);
            LogLevel level = LogLevel.getLevel(StringUtil.getStr(map.get("level"), "all"));
            var log = createLog(fileSetting, logName, level, datePattern);
            logMap.put(logName, log);
        });*/
    }
    private static Log createLog(LogSetting logSetting) {
        /*var logName=logSetting.logName();



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
        return new Log(logger);*/
        return null;
    }

}
