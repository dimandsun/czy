package com.czy.log;

import com.czy.util.io.FileUtil;
import com.czy.util.json.JsonUtil;
import com.czy.util.model.SettingFile;

import java.util.*;

/**
 * @author chenzy
 * @date 2020-08-06
 */
public class LogFactory {

    private LogFactory() {}
    private static Map<String, Log> logMap = Collections.synchronizedMap(new HashMap<>());
    public static Log getLog(String logName) {
        if (logMap.containsKey(logName)) {
            return logMap.get(logName);
        }
        var log = logMap.get(logName);
        if (log == null) {
            //不写日志文件，只是控制台打印
            var logSetting=new LogSetting(logName,"%d{yyyyMMddHH} [%thread] [%level]: %msg%n",null,LogLevel.ALL,true);
            log=new Log(logSetting);
            logMap.put(logName, log);
        }
        return logMap.get(logName);
    }
    public static Log getLog(Class c) {
        return getLog(c.getSimpleName());
    }
    private static void initLog(SettingFile settingFile) {
        Objects.requireNonNull(settingFile);
        var file = FileUtil.getResourceFile(settingFile.moduleDir(),settingFile.fileName());
        FileUtil.readYML(file).map(map -> (List<Map<String, Object>>) map.get("logs")).get().forEach(map -> {
            var logSetting=JsonUtil.map2Model(map,LogSetting.class);
            logMap.put(logSetting.logName(), new Log(logSetting));
        });
    }

}
