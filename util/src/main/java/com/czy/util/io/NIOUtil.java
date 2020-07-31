package com.czy.util.io;

import com.czy.util.text.Line;

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
        var separatorBytes = Line.separator.getBytes();
        try {
            var buffer = ByteBuffer.allocate(1024 * 4);
            ByteBuffer lineBuffer = ByteBuffer.allocate(20);
            List<String> data = new ArrayList<>();
            InputStream is = socketChannel.socket().getInputStream();
            ReadableByteChannel readCh = Channels.newChannel(is);
            end:
            while (readCh.read(buffer) != -1) {
                buffer.flip();
                while (buffer.hasRemaining()) {
                    //空间不够扩容
                    if (!lineBuffer.hasRemaining()) {
                        lineBuffer = reAllocate(lineBuffer);
                    }
                    byte b = buffer.get();
                    /*一行一行读取*/
                    if (separatorBytes.length == 2) {
                        /*windows下的文本文件换行符：\r\n*/
                        if (b == 13 && buffer.hasRemaining() && buffer.get() == 10) {
                            if (addLine(charSet, lineBuffer, data)) {
                                break;
                            }
                        }
                    } else if (separatorBytes[0] == 13) {
                        /*linux/unix下的文本文件换行符：\r*/
                        if (b == 13) {
                            if (addLine(charSet, lineBuffer, data)) {
                                break end;
                            }
                        }
                    } else if (separatorBytes[0] == 10) {
                        /*Mac下的文本文件换行符：\n*/
                        if (b == 10) {
                            if (addLine(charSet, lineBuffer, data)) {
                                break end;
                            }
                            continue;
                        }
                    }
                    lineBuffer.put(b);

                }
                buffer.clear();
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
    private static Boolean addLine(String charSet, ByteBuffer lineBuffer, List<String> data) {
        lineBuffer.flip();
        var line = Charset.forName(charSet).decode(lineBuffer).toString();
        data.add(line);
        lineBuffer.clear();
        return line.length() == 0;
    }

    /**
     * 把文本都写入out
     *
     * @param data
     * @param out
     */
    public static void write(String data, ByteChannel out) {
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
        if (file == null || !file.exists()) {
            write(data, out);
            return;
        }
        try {
            var buffer = putByte(data.getBytes(), ByteBuffer.allocate(1024 * 4));
            buffer.flip();
            out.write(buffer);
            buffer.clear();
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
