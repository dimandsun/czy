package com.czy.javaLog;


import java.util.logging.Logger;

/**
 * @author chenzy
 * @date 2020-07-17
 */
@Deprecated
public class Log{
    private Logger logger;
    public Log(Logger logger) {
        this.logger = logger;
    }
    public void log(String msg,Throwable throwable){
        log(LogLevel.ALL,msg,throwable);
    }
    public void log(String msg,Object... parms){
        log(LogLevel.ALL,msg,parms);
    }
    public void debug(String msg,Throwable throwable){
        log(LogLevel.DEBUG,msg,throwable);
    }
    public void debug(String msg,Object... parms){
        log(LogLevel.DEBUG,msg,parms);
    }
    public void info(String msg,Throwable throwable){
        log(LogLevel.INFO,msg,throwable);
    }
    public void info(String msg,Object... parms){
        log(LogLevel.INFO,msg,parms);
    }
    public void warn(String msg,Throwable throwable){
        log(LogLevel.WARN,msg,throwable);
    }
    public void warn(String msg,Object... parms){
        log(LogLevel.WARN,msg,parms);
    }
    public void error(String msg,Throwable throwable){
        log(LogLevel.ERROR,msg,throwable);
    }
    public void error(String msg,Object... parms){
        log(LogLevel.ERROR,msg,parms);
    }
    public void error(Throwable throwable,String msg,Object... parms){
        log(LogLevel.ERROR,msg,parms);
    }
    private void log(LogLevel level,String msg,Throwable throwable){
        logger.log(level.getLevel(),msg,throwable);
    }
    private void log(LogLevel level,String msg,Object... parms){
        logger.log(level.getLevel(),msg,parms);
    }
}
