package com.czy.util.io;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author chenzy
 * @since 2020-06-18
 */
public class IOUtil {
    private IOUtil(){}
    /**
     * 关闭流,这个方法没有什么用，try(){}catch(){}就可以实现此功能
     *
     * @return
     */
    public static Boolean close(Closeable... streams) {
        if (streams == null || streams.length < 1) {
            return true;
        }
        try {
            for (Closeable stream : streams) {
                if (stream != null) {
                    stream.close();
                }
            }
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
