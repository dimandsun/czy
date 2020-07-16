package com.czy.log;

import java.io.File;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

import static java.util.logging.Level.WARNING;

/**
 * @author chenzy
 * @date 2020-07-16
 */
public class LoggerFactory {
    public static Logger getLogger() {
        return getLogger("log");
    }
    public static Logger getLogger(String name) {
        var logger=Logger.getLogger(name);
        FileHandler fileHandler = null;
        try {
            String root=new File("").getCanonicalPath().substring(0,2);
            fileHandler = new FileHandler(root+File.separator+"log"+File.separator+"log.log",true);
        } catch (IOException e) {
            e.printStackTrace();
        }
        var formatter = new LogFormatter();
        logger.setLevel(Level.ALL);
        fileHandler.setFormatter(formatter);
        logger.addHandler(fileHandler);
        return logger;
    }
    public static Logger getLogger(Class clases) {
        return null;
    }
}
