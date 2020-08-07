package com.czy.log;

/**
 * @author chenzy
 * @date 2020-08-07
 */
public record LogSetting(String logName,String datePattern,String filePath,String level,int fileSize) {

}
