package nio;

import com.czy.util.io.FileUtil;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 * @author chenzy
 * @since 2020-06-18
 * 缓冲区：buffer 在NIO中负责数据的存取。
 * 数组，用于存储不同数据类型的数据。除boolean外的基本数据类型都有对应的Buffer
 * ByteBuffer
 * CharBuffer
 * ShortBuffer
 * IntBuffer
 * LongBuffer
 * FloatBuffer
 * DoubleBuffer
 * Buffer中四个属性
 * capacity：容量，最大存储容量，声明后不能改变
 * limit：   界限，可读写数据大小，limit后的数据不能读写
 * position  正在读写数据的位置
 * mark      记录当前position，通过reset恢复position到mark记录值
 * position<=limit<=capacity
 * 非直接缓冲区：通过allocate()分配缓冲区，将缓冲区建立在JVM内存中
 * 直接缓冲区: 通过allocateDirect()分配缓冲区，将缓冲区建立在物理内存中。可以提高效率。
 * 进行分配和取消分配所需成本通常高于非直接缓冲区。
 * 内容可以驻留在常规的垃圾回收堆外，对应用程序内存需求量的影响不明显。
 * 建立分配给那些易受基础系统的本机IO操作影响 的大型、持久缓冲区。
 * 能提高程序性能时分配直接缓冲区
 * 弊端：不安全、消耗大、不能直接释放资源（交由操作系统控制。等待垃圾回收器清除，System.gc()可以加快垃圾回收机制回收，但是依然不确定会立即回收）
 * <p>
 * 可以通过FileChannel.map()将文件区域直接映射到内存中创建直接缓冲区。此方法返回MappedByteBuffer。
 * 若直接缓冲区有不可访问的内存区域，会在某个时间抛出异常。
 * isDirect()判断是否是直接缓冲区
 * <p>
 * 通道 Channel 源节点与目标节点的连接。NIO中负责缓存区数据的传输，本身不存储数据
 * FileChannel
 * SocketChannel 用于socket(tcp)
 * ServerSocketChannel
 * DatagramChannel 用于dcp
 * 获取通道
 * 支持通道的类.getChannel();
 * 本地IO
 * FileInputStream()/FileOutputStream()
 * RandomAccessFile();
 * 网络IO
 * Socket
 * ServerSocket
 * DatagramSocket
 * jdk7 NIO.2对各个通道提供了类.open()
 * jdk7 NIO.2的Files.newByteChannel()
 * 通道间数据传输
 * transferFrom()
 * transferTo()
 *
 * 分散Scatter与聚集Gather
 *
 */
public class TestBuffer {
    @Test
    public void te() {
        //分配指定大小缓冲区
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);

        //存入数据
        byteBuffer.put("abcd爱的".getBytes());

        //切换模式 limit变成buffer中的实际容量
        byteBuffer.flip();
        byteBuffer.mark();
        var b = new byte[byteBuffer.limit()];
        //读数据 position改变
        byteBuffer.get(b);
        System.out.println(new String(b));
        //重读数据：position变为0
        byteBuffer.rewind();
        //清空 四个属性重置。buffer中数据依然存在，但是处于被遗忘状态
        byteBuffer.clear();
        var c = new byte[byteBuffer.limit()];
        byteBuffer.get(c);
        System.out.println(new String(c));
        //恢复到mark的位置 clear()后 mark值重置，reset异常
        byteBuffer.reset();

        //是否还有可操作数据
        byteBuffer.hasRemaining();
        //可操作数量
        byteBuffer.remaining();
    }

    @Test
    public void testDirBuffer() {
        var buffer = ByteBuffer.allocateDirect(1024);
        System.out.println(buffer.isDirect());

    }

    /*非直接缓冲区*/
    @Test
    public void testChannel() throws IOException {
//        var fileInputStream=new FileInputStream("girl.png");
        var fileInputStream = new FileInputStream(FileUtil.getResourceFile("girl.png"));
        var file = FileUtil.getResourceFile("2.png");
        var fileOutputStream = new FileOutputStream(file);
        var inChannel = fileInputStream.getChannel();
        var outChannel = fileOutputStream.getChannel();

        var buffer = ByteBuffer.allocate(1024);
        while (inChannel.read(buffer) != -1) {
            buffer.flip();
            outChannel.write(buffer);
            buffer.clear();
        }
        inChannel.close();
        outChannel.close();
        fileInputStream.close();
        fileOutputStream.close();
    }

    /*直接缓冲区完成文件的复制(内存映射文件)*/
    @Test
    public void testChannel2() throws IOException {
        var inChannel = FileChannel.open(Paths.get("girl1.png"), StandardOpenOption.READ);
        //StandardOpenOption.CREATE 文件不存在则创建
        var outChannel = FileChannel.open(Paths.get("4.png"), StandardOpenOption.WRITE,StandardOpenOption.READ, StandardOpenOption.CREATE);

        //内存映射文件
        var inMappedBuffer = inChannel.map(FileChannel.MapMode.READ_ONLY, 0, inChannel.size());
        var outMappedBuffer = outChannel.map(FileChannel.MapMode.READ_WRITE, 0, inChannel.size());
        //直接读写缓冲区
        var dst=new byte[inMappedBuffer.limit()];

        inMappedBuffer.get(dst);
        outMappedBuffer.put(dst);

        inChannel.close();
        outChannel.close();
    }


    /*直接缓冲区完成文件的复制:通道间数据传输*/
    @Test
    public void testChannel3() throws IOException {
        var inChannel = FileChannel.open(Paths.get("girl1.png"), StandardOpenOption.READ);
        var outChannel = FileChannel.open(Paths.get("4.png"), StandardOpenOption.WRITE,StandardOpenOption.READ, StandardOpenOption.CREATE);
        inChannel.transferTo(0,inChannel.size(),outChannel);
        inChannel.close();
        outChannel.close();
    }

    /*直接缓冲区完成文件的复制:通道间数据传输*/
    @Test
    public void testChannel4() throws IOException {
        var inChannel = FileChannel.open(Paths.get("girl1.png"), StandardOpenOption.READ);
        var outChannel = FileChannel.open(Paths.get("4.png"), StandardOpenOption.WRITE,StandardOpenOption.READ, StandardOpenOption.CREATE);
        outChannel.transferFrom(inChannel,0,inChannel.size());
        inChannel.close();
        outChannel.close();
    }
}
