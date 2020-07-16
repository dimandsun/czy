package com.czy.log;

import com.czy.util.io.FileUtil;

import javax.swing.filechooser.FileSystemView;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

import static java.util.logging.Level.WARNING;

/**
 * @author chenzy
 * @date 2020-07-16
 */
public class Main {
    Logger logger=LoggerFactory.getLogger();

    public static void main(String[] args) throws IOException {
        var logger=Logger.getLogger("czy");
        var errorLog=Logger.getLogger("czy123");
        var fileHandler = new FileHandler("/logs/log.log",true);
        var formatter = new LogFormatter();
        logger.setLevel(Level.INFO);
        errorLog.setLevel(WARNING);
        fileHandler.setFormatter(formatter);
        logger.addHandler(fileHandler);
        errorLog.addHandler(fileHandler);

        logger.info("cesasd爱的方式");
        errorLog.warning("asda");
    }
}
