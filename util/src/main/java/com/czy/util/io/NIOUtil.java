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
    public static List<String> readByLine(String charSet, SocketChannel socketChannel) {
        try {
            int bufferLength =100;// 1024 * 4;
            int lineLength = 64;
            var buffer = ByteBuffer.allocate(bufferLength);
            ByteBuffer lineBuffer = ByteBuffer.allocate(lineLength);
            List<String> data = new ArrayList<>();
            InputStream is = socketChannel.socket().getInputStream();
            ReadableByteChannel readCh = Channels.newChannel(is);
            int length = -1;
            end:
            while ((length = readCh.read(buffer)) != -1) {
                buffer.flip();
                while (buffer.hasRemaining()) {
                    //空间不够扩容
                    if (!lineBuffer.hasRemaining()) {
                        lineBuffer = reAllocate(lineBuffer);
                    }
                    byte b = buffer.get();
                    if (b == 13 && buffer.hasRemaining() && buffer.get() == 10) {
                        addLine(charSet, lineBuffer, data);
                    } else {
                        lineBuffer.put(b);
                    }
                }
                buffer.clear();
                /*数据没有把buffer放满，说明数据已经读到尾了，直接跳出循环。如果不跳出的话，会阻塞
                存在问题，当数据刚好把buffer装满，则此请求会阻塞。
                * */
                if (length < bufferLength) {
                    break end;
                }
            }
            return data;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 达到末尾时，返回true
     *
     * @param charSet
     * @param lineBuffer
     * @param data
     * @return
     */
    private static void addLine(String charSet, ByteBuffer lineBuffer, List<String> data) {
        lineBuffer.flip();
        var line = Charset.forName(charSet).decode(lineBuffer).toString();
        data.add(line);
        lineBuffer.clear();
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
            var buffer = putByte(data.getBytes(), ByteBuffer.allocate(1024 * 4));
            buffer.flip();
            out.write(buffer);
            buffer.clear();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 文本和file内容都写入out
     *
     * @param data
     * @param file
     * @param out
     */
    public static void write(String data, File file, ByteChannel out) {
        write(data, out);
        write(file, out);
    }

    public static void write(File file, ByteChannel out) {
        if (file == null || !file.exists()) {
            return;
        }
        try {
            var buffer = ByteBuffer.allocate(1024 * 4);
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
        return putByte(Arrays.copyOfRange(data, 0, data.length - capacity), reAllocate(byteBuffer));
    }

    /**
     * 扩容
     *
     * @param stringBuffer
     * @return
     */
    public static ByteBuffer reAllocate(ByteBuffer stringBuffer) {
        final int capacity = stringBuffer.capacity();
        byte[] newBuffer = new byte[capacity * 2];
        System.arraycopy(stringBuffer.array(), 0, newBuffer, 0, capacity);
        return ByteBuffer.wrap(newBuffer).position(capacity);
    }


}
