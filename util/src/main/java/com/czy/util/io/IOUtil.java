package com.czy.util.io;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author chenzy
 * @since 2020-06-18
 */
public class IOUtil {
    private IOUtil(){}
    /**
     * 关闭流
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
    /*读取一行：注意，若流中没有换行字符，会永远阻塞*/
    public static String readLine(BufferedReader reader){
//        var reader1=new BufferedReader(new InputStreamReader(reader));
        try {
            String line = null;
            while ((line=reader.readLine())!=null){
                return line;
            }
            return "";
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }
    /*按行读取流中所有数据。注意，若流中没有换行字符，会永远阻塞*/
    public static List<String> readAllLine(BufferedReader reader){
        var lineList = new ArrayList<String>();
        try {
            String line =null;
            while ((line=reader.readLine())!=null){
                lineList.add(line);
            }
            return lineList;
        } catch (IOException e) {
            e.printStackTrace();
            return lineList;
        }
    }
}
