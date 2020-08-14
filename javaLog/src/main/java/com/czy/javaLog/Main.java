package com.czy.javaLog;

import com.czy.util.time.TimeUtil;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

import static java.util.logging.Level.WARNING;

/**
 * @author chenzy
 * @date 2020-07-16
 */
@Deprecated
public class Main {
    public static void main(String[] args) throws IOException {
        var logger=Logger.getLogger("czy");
        var errorLog=Logger.getLogger("czy123");
        var fileHandler = new FileHandler("E:/logs/log.log",true);
        var formatter = new LogFormatter(TimeUtil.DEFAULT_FORMAT);
        logger.setLevel(Level.INFO);
        errorLog.setLevel(WARNING);
        fileHandler.setFormatter(formatter);
        logger.addHandler(fileHandler);
        errorLog.addHandler(fileHandler);

        logger.info("cesasd爱的方式");
        errorLog.warning("asda");
    }
}
