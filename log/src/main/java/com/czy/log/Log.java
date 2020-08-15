package com.czy.log;

import com.czy.util.io.FileUtil;
import com.czy.util.io.NIOUtil;
import com.czy.util.text.Line;
import com.czy.util.text.StringUtil;
import com.czy.util.time.TimeUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author chenzy
 * @date 2020-08-07
 */
public class Log {
    private static ReentrantLock lock = new ReentrantLock();
    private LogSetting logSetting;
    private FileChannel fileChannel;
    private Boolean start=true;
    public void start() {
         this.start=true;
    }
    public void stop() {
        this.start = false;
    }

    public Log(LogSetting logSetting) {
        this.logSetting = logSetting;
    }

    public void error(String msg, Throwable throwable, Object... parms) {
        log(LogLevel.ERROR, msg + throwable, parms);
    }

    public void error(String msg, Object... parms) {
        log(LogLevel.ERROR, msg, parms);
    }

    public void warn(String msg, Throwable throwable, Object... parms) {
        log(LogLevel.WARN, msg + throwable, parms);
    }

    public void warn(String msg, Object... parms) {
        log(LogLevel.WARN, msg, parms);
    }

    public void info(String msg, Throwable throwable, Object... parms) {
        log(LogLevel.INFO, msg + throwable, parms);
    }

    public void info(String msg, Object... parms) {
        log(LogLevel.INFO, msg, parms);
    }

    public void debug(String msg, Throwable throwable, Object... parms) {
        log(LogLevel.DEBUG, msg + throwable, parms);
    }

    public void debug(String msg, Object... parms) {
        log(LogLevel.DEBUG, msg, parms);
    }

    public void log(String msg, Throwable throwable, Object... parms) {
        log(LogLevel.ALL, msg + throwable, parms);
    }

    public void log(String msg, Object... parms) {
        log(LogLevel.ALL, msg, parms);
    }

    private void log(LogLevel level, String msg, Throwable throwable, Object... parms) {
        log(level, msg + throwable, parms);
    }

    private void createFile() {
        if (StringUtil.isBlank(logSetting.filePath())) {
            return;
        }
        /*文件路径现在只支持按时间生成*/
        var file = new File(replaceNowStr(logSetting.filePath()));
        /*判断当前文件是否存在，如果不存在，创建*/
        FileUtil.createFile(file);
        /*重新获取fileChannel对象*/
        if (fileChannel == null && file.exists()) {
            try {
                fileChannel = new FileOutputStream(file, true).getChannel();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    private void log(LogLevel level, String msg, Object... parms) {
        if (!start){
            return;
        }
        if (LogInfo.instance().usePool()) {
            LogFactory.executor().execute(new LogTask(level, msg, parms));
        } else {
            write(level, msg, parms);
        }

    }

    private String replaceNowStr(String pattern) {
        var beginIndex = pattern.indexOf("%d{");
        if (beginIndex != -1) {
            var nowStr = TimeUtil.nowStr(pattern.substring(beginIndex + 3, pattern.indexOf("}")));
            pattern = pattern.replaceAll("%d\\{(.*)\\}", nowStr);
        }
        return pattern;
    }

    private void write(LogLevel level, String msg, Object... parms) {
        lock.lock();
        try {
            createFile();
//        /*日志级别大于等于设置级别才能输出*/
            if (level.compareTo(logSetting.level()) >= 0) {
                /*拼接要打印的信息*/
                var data = StringUtil.join(msg, parms);
                var result = logSetting.dataPattern();
                result = replaceNowStr(result);
                result = result.replaceAll("%thread", Thread.currentThread().getName());
                result = result.replaceAll("%level", level.name());
                result = result.replaceAll("%msg", data);
                result = result.replaceAll("%n", Line.separator);
                if (logSetting.consolePrint()) {
                    /*控制台打印*/
                    System.out.print(result);
                }
                if (fileChannel == null || !fileChannel.isOpen()) {
                    return;
                }
                /*写入文件*/
                fileChannel.write(ByteBuffer.wrap(result.getBytes()));
                fileChannel.force(true);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    private class LogTask implements Runnable {
        private LogLevel level;
        private String msg;
        private Object[] parms;

        public LogTask(LogLevel level, String msg, Object[] parms) {
            this.level = level;
            this.msg = msg;
            this.parms = parms;
        }

        @Override
        public void run() {
            write(level, msg, parms);
        }
    }
}
