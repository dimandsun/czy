package com.czy.log;

import com.czy.util.model.Par;

/**
 * @author chenzy
 * @date 2020-08-07
 */
public record LogSetting(String logName,String dataPattern, String filePath, LogLevel level,boolean consolePrint ) {
}
