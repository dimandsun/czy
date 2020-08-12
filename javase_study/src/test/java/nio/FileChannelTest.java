package nio;

import org.junit.Test;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Path;
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
        var fileChannel = new FileOutputStream("a.text").getChannel();
        fileChannel.position(12);
        System.out.println(fileChannel.size());
        /*
        fileChannel.write(ByteBuffer.wrap("abcd".getBytes()),2);
        等价于
         fileChannel.write(ByteBuffer.wrap("abcd".getBytes()));
         fileChannel.position(2);
        */

        fileChannel.write(ByteBuffer.wrap("abcd".getBytes()), 2);
//        fileChannel.position(2);
        fileChannel.write(ByteBuffer.wrap("abcd".getBytes()));
        System.out.println(fileChannel.position());

    }

    @Test
    public void read() throws IOException {
        var fileChannel = new FileInputStream("a.text").getChannel();
        System.out.println(fileChannel.size());
        var buffer = ByteBuffer.allocate((int) fileChannel.size());
        int length = 0;
        while ((length = fileChannel.read(buffer)) != -1) {
            System.out.println(length);
            buffer.flip();
            var bytes = new byte[buffer.limit()];
            buffer.get(bytes);
            System.out.println(new String(bytes));
            buffer.clear();
        }
        System.out.println(length);
    }

    @Test
    public void truncate() throws IOException {
        var byteBuffer = ByteBuffer.wrap("123".getBytes());
        var channel = new FileOutputStream("a.text").getChannel();
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
        fileChannel1.transferTo(0, fileChannel1.size(), fileChannel2);
    }

    @Test
    public void lock() {
        try (var fileChannel1 = new RandomAccessFile("a.text", "r").getChannel()) {
            fileChannel1.lock();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*文件不存在则创建.但是文件夹不在的话抛出异常，*/
    @Test
    public void createFile() {
        //只有CREATE权限，在创建文件时会报错
        try (var f = FileChannel.open(Paths.get("a/a.text"), StandardOpenOption.CREATE, StandardOpenOption.WRITE)) {

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*文件不存在则创建.但是文件在的话抛出异常
    创建的文件是稀疏文件：使不存储数据的空间不占用硬盘容量，写入有效数据时再占用硬盘容量
    */
    @Test
    public void createNewFile() {
        //只有CREATE权限，在创建文件时会报错
        try (var f = FileChannel.open(Paths.get("aaa.text"), StandardOpenOption.CREATE_NEW, StandardOpenOption.WRITE)) {

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*创建稀疏文件，不支持稀疏文件的系统忽略该选项。window不支持稀疏文件*/
    @Test
    public void SPARSE() {
        try (var f=FileChannel.open(Paths.get("aaa.text"),StandardOpenOption.SPARSE, StandardOpenOption.CREATE_NEW,StandardOpenOption.WRITE)){
            f.position(1024*1024*1024);
            f.write(ByteBuffer.wrap("123".getBytes()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*追加:文件不在时创建*/
    @Test
    public void append() {
        try (var f = FileChannel.open(Paths.get("a/abc.text"), StandardOpenOption.CREATE, StandardOpenOption.WRITE, StandardOpenOption.APPEND)) {
            f.write(ByteBuffer.wrap("123".getBytes()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void readFile() {
        File file = new File("abc.text");
        try (var f = FileChannel.open(file.toPath(), StandardOpenOption.READ)) {
            var byteBuffer = ByteBuffer.allocate((int) file.length());
            f.read(byteBuffer);
            System.out.println(new String(byteBuffer.array()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*截断文件长度为0：清空文件内容*/
    @Test
    public void TRUNCATE_EXISTING() {
        File file = new File("abc.text");
        try (var f = FileChannel.open(file.toPath(), StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.WRITE)) {
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*关闭通道时删除文件*/
    @Test
    public void DELETE_ON_CLOSE() {
        File file = new File("abc.text");
        try (var f = FileChannel.open(file.toPath(), StandardOpenOption.DELETE_ON_CLOSE)) {
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /*文件内容每次更新都同步写入底层设备:文件内容和元数据同步更新到底层存储设备
    等价于force(true)
    * */
    @Test
    public void SYNC() {
        try (var f = FileChannel.open(Paths.get("a.text"),StandardOpenOption.SYNC, StandardOpenOption.CREATE, StandardOpenOption.WRITE)) {

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /*DSYNC与SYNC相反，只更新内容。等价于force(false)*/
    @Test
    public void DSYNC() {
        try (var f = FileChannel.open(Paths.get("a.text"),StandardOpenOption.DSYNC, StandardOpenOption.CREATE, StandardOpenOption.WRITE)) {
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /*判断通道是否打开*/
    @Test
    public void isOpen() {
        try (var f = FileChannel.open(Paths.get("a.text"),StandardOpenOption.CREATE, StandardOpenOption.WRITE)) {
            System.out.println(f.isOpen());
        } catch (IOException e) {
            e.printStackTrace();
        }finally {

        }
    }
}
