package nio;

import org.junit.Test;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 * @author chenzy
 * @date 2020-08-05
 */

/*
 FileChannel：
 获取对象：
    FileOutputStream.getChannel();
 常用方法：
    size();//通道关联文件的大小，read流的通道为实际文件的大小，write流通道在写入数据前为0，写入后为position()值
    position()：通道当前位置
    write(byteBuffer);//从通道当前位置开始写入缓冲区内的字节数组（缓冲区的position-limit间的数据），返回写入的字节长度
    write(byteBuffer[]);
    read(byteBuffer);//从通道当前位置开始读取数据，把数据放入缓冲区，返回读取数据的长度，0通常代表缓冲区已满，-1表示读取到流的末端
    read(byteBuffer[]);

    truncate(size);//截断缓冲区为指定大小
 */
public class FileChannelTest {
    @Test
    public void write() throws IOException {
        //position为0
        var fileChannel=new FileOutputStream("a.text").getChannel();
        fileChannel.position(12);
        System.out.println(fileChannel.size());
        /*
        fileChannel.write(ByteBuffer.wrap("abcd".getBytes()),2);
        等价于
         fileChannel.write(ByteBuffer.wrap("abcd".getBytes()));
         fileChannel.position(2);
        */

        fileChannel.write(ByteBuffer.wrap("abcd".getBytes()),2);
//        fileChannel.position(2);
        fileChannel.write(ByteBuffer.wrap("abcd".getBytes()));
        System.out.println(fileChannel.position());

    }

    @Test
    public void read() throws IOException {
        var fileChannel=new FileInputStream("a.text").getChannel();
        System.out.println(fileChannel.size());
        var buffer=ByteBuffer.allocate(1024);
        int length=0;
        while ((length=fileChannel.read(buffer))!=-1){
            System.out.println(length);
            buffer.flip();
            var bytes=new byte[buffer.limit()];
            buffer.get(bytes);
            System.out.println(new String(bytes));
            buffer.clear();
        }
        System.out.println(length);
    }

    @Test
    public void truncate() throws IOException {
        var byteBuffer=ByteBuffer.wrap("123".getBytes());
        var channel=new FileOutputStream("a.text").getChannel();
        channel.write(byteBuffer);
        channel.truncate(1);
    }

    @Test
    public void transferTo() throws IOException {
        /*
        a.text的数据写入b.text
        */
        var fileChannel1 = new RandomAccessFile("a.text", "r").getChannel();
        var fileChannel2 = new RandomAccessFile("b.text", "rw").getChannel();
        fileChannel1.transferTo(0,fileChannel1.size(),fileChannel2);
    }

    @Test
    public void lock() {
        try(var fileChannel1 = new RandomAccessFile("a.text", "r").getChannel()){
            fileChannel1.lock();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void open() {
        try (var f=FileChannel.open(Paths.get("a.text"), StandardOpenOption.READ)){

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
