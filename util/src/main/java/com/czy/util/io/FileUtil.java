package com.czy.util.io;

import java.io.*;

/**
 * @author chenzy
 * @date 2020-08-05
 * NIO操作
 */
public class FileUtil {
    private FileUtil(){}

    /**
     * from文件数据拷贝到to文件
     * 此方法只支持复制utf-8格式编码文件
     * @param from
     * @param to
     */
    public static void copy(File from,File to){
        try(var reader = new BufferedReader(new InputStreamReader(new FileInputStream(from)));
            var writer = new PrintWriter(to)){
            reader.transferTo(writer);
            writer.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
