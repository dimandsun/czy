package com.czy.util.io;

import com.czy.util.text.Line;
import com.czy.util.text.StringUtil;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.charset.Charset;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * @author chenzy
 * @since 2020-06-18
 */
public class NIOUtil {
    private NIOUtil() {

    }

    //按行读取数据
    public static List<String> readByLine(String charSet, SocketChannel channel) {
        try {
            int bufferLength =256;// 1024 * 4;
            int lineLength = 64;
            var buffer = ByteBuffer.allocate(bufferLength);
            ByteBuffer lineBuffer = ByteBuffer.allocate(lineLength);
            List<String> data = new ArrayList<>();
            int len = 0;
            while ((len = channel.read(buffer)) != 0) {
                buffer.flip();
                while (buffer.hasRemaining()) {
                    //空间不够扩容
                    if (!lineBuffer.hasRemaining()) {
                        lineBuffer = extend(lineBuffer);
                    }
                    byte b = buffer.get();
                    if (b == 13 && buffer.hasRemaining() && buffer.get() == 10) {
                        data.add(read(charSet, lineBuffer));
                    } else {
                        lineBuffer.put(b);
                    }
                }
                System.out.println(new String(buffer.array(), 0, len));
                buffer.clear();
            }
            return data;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    /**
     * 一次读取全部数据
     * @param charSet
     * @param buffer
     * @return
     */
    private static String read(String charSet, ByteBuffer buffer){
        buffer.flip();
        var line = Charset.forName(charSet).decode(buffer).toString();
        buffer.clear();
        return line;
    }

    /**
     * 把文本都写入out
     *
     * @param data
     * @param out
     */
    public static void write(String data, ByteChannel out) {
        if (StringUtil.isBlank(data)) {
            return;
        }
        try {
            out.write(ByteBuffer.wrap(data.getBytes()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void write(File file, ByteChannel out) {
        if (file == null || !file.exists()) {
            return;
        }
        try {
            var buffer = ByteBuffer.allocate((int) file.length());
            if (file != null) {
                var fileChannel = FileChannel.open(file.toPath(), StandardOpenOption.READ);
                while (fileChannel.read(buffer) != -1) {
                    buffer.flip();
                    out.write(buffer);
                    buffer.clear();
                }
                fileChannel.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 将字节数组放入byteBuffer
     * 因为数据量可以超过byteBuffer的容量，需要分割字节数据并对byteBuffer扩容，且使用递归
     *
     * @param data
     * @param byteBuffer
     */
    private static ByteBuffer putByte(byte[] data, ByteBuffer byteBuffer) {
        Objects.requireNonNull(byteBuffer);
        var capacity = byteBuffer.limit();
        if (data == null || data.length == 0 || capacity <= 0) {
            return byteBuffer;
        }
        if (data.length <= capacity) {
            byteBuffer.put(data);
            return byteBuffer;
        }
        var data1 = Arrays.copyOfRange(data, 0, capacity);
        byteBuffer.put(data1);
        return putByte(Arrays.copyOfRange(data, 0, data.length - capacity), extend(byteBuffer));
    }


    /**
     * 扩容
     * @param buffer
     * @return
     */
    public static ByteBuffer extend(ByteBuffer buffer){
        return ByteBuffer.allocate(buffer.capacity()*2).put(buffer);
    }
    /**
     * 扩容
     * @param buffer
     * @return
     */
    public static ByteBuffer extend(ByteBuffer buffer,int extendSize){
        return ByteBuffer.allocate(buffer.capacity()+extendSize).put(buffer);
    }

}
