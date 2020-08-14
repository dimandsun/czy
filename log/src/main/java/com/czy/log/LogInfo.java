package com.czy.log;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @author chenzy
 * @date 2020-08-14
 */
public class LogInfo {
    private static LogInfo instance=new LogInfo();
    private LogInfo(){}
    public static LogInfo instance(){
        return instance;
    }
    private boolean usePool;
    private Map<String, Log> logMap = Collections.synchronizedMap(new HashMap<>());

    public Boolean usePool() {
        return usePool;
    }
    public void usePool(Boolean usePool) {
        this.usePool = usePool;
    }
    public void add(String logName,Log log) {
        logMap.put(logName,log);
    }
    public Log get(String logName) {
        if (logMap.containsKey(logName)) {
            return logMap.get(logName);
        }
        var log = logMap.get(logName);
        if (log == null) {
            //不写日志文件，只是控制台打印
            var logSetting = new LogSetting(logName, "%d{yyyyMMddHHmmssSSS} [%thread] [%level]: %msg%n", null, LogLevel.ALL, true);
            log = new Log(logSetting);
            logMap.put(logName, log);
        }
        return logMap.get(logName);    }

    public void close() {
        logMap=null;
    }
}
