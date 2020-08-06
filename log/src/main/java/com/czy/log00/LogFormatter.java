package com.czy.log00;

import com.czy.util.time.TimeUtil;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.logging.*;

/**
 * @author chenzy
 * @date 2020-07-16
 */
public class LogFormatter extends Formatter {
    //日期格式
    private String datePattern;

    public LogFormatter(String datePattern) {
        this.datePattern = datePattern;
    }

    @Override
    public String format(LogRecord record) {
        String date = TimeUtil.nowStr(datePattern);
        String source;
        if (record.getSourceClassName() != null) {
            source = record.getSourceClassName();
            if (record.getSourceMethodName() != null) {
                source += "." + record.getSourceMethodName();
            }
        } else {
            source = record.getLoggerName();
        }
        String message = formatMessage(record);
        String throwable = "";
        if (record.getThrown() != null) {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            pw.println();
            record.getThrown().printStackTrace(pw);
            pw.close();
            throwable = sw.toString();
        }
        String level=record.getLevel().getName().toLowerCase();
        if ("".equals(throwable)){
            return String.format("%s [%s] %s: %s%n",date,level,source,message);
        }else {
            return String.format("%s [%s] %s: %s%n%s%n",date,level,source,message, throwable);
        }
    }
}
