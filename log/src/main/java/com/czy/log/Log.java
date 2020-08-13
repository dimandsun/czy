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

/**
 * @author chenzy
 * @date 2020-08-07
 */
public class Log {
    private LogSetting logSetting;
    private FileChannel fileChannel;
    private ByteBuffer buffer;

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
    private void createFile(){
        if (StringUtil.isBlank(logSetting.filePath())){
            return;
        }
        /*文件路径现在只支持按时间生成*/
        var file = new File(TimeUtil.nowStr(logSetting.filePath()));
        /*判断当前文件是否存在，如果不存在，创建且重新获取fileChannel对象*/
        if (FileUtil.createFile(file)) {
            try {
                fileChannel = new FileOutputStream(file).getChannel();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
    private void log(LogLevel level, String msg, Object... parms){
        createFile();
        if (fileChannel==null||!fileChannel.isOpen()){
            return;
        }
        /*日志级别大于等于设置级别才能输出*/
        if (level.compareTo(logSetting.level()) >= 0) {
            /*拼接要打印的信息*/
            var data = StringUtil.join(msg, parms);
            var result = logSetting.dataPattern();
            result = TimeUtil.nowStr(result);
            result = result.replaceAll("%thread", Thread.currentThread().getName());
            result = result.replaceAll("%level", level.name());
            result = result.replaceAll("%msg", data);
            result = result.replaceAll("%n", Line.separator);
            if (logSetting.consolePrint()) {
                /*控制台打印*/
                System.out.print(result);
            }
            try {
                var bytes = result.getBytes();
                if (buffer == null) {
                    buffer = ByteBuffer.wrap(bytes);
                } else {
                    /*buffer容量不够时，扩容*/
                    if (bytes.length > buffer.limit()) {
                        buffer = NIOUtil.extend(buffer);
                    }
                    buffer.put(bytes);
                }
                buffer.flip();
                /*写入文件*/
                fileChannel.write(buffer);
//                fileChannel.force(true);
                buffer.clear();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
