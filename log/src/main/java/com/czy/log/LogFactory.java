package com.czy.log;

import com.czy.log00.Log;

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
            log = new Log(logger);
            logMap.put(logName, log);
        }
        return logMap.get(logName);
    }
    public static Log getLog(Class c) {
        return getLog(c.getSimpleName());
    }



}
