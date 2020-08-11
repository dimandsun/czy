package nio;

import com.czy.util.io.FileUtilOld;
import org.junit.Test;

import java.io.*;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.*;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 * @author chenzy
 * @since 2020-06-18
 */
/*
传统IO： 面向流
            阻塞IO
NIO：    面向缓冲区：通道Channel不带数据，缓冲区Buffer装载数据。
               通道：打开IO设备(file/socket)的连接。负责传输
               缓冲区：负责存储
            非阻塞
            选择器
            获取channel、buffer——》操作buffer——》处理数据

非直接缓冲区：通过allocate()分配缓冲区，将缓冲区建立在JVM内存中
直接缓冲区: 通过allocateDirect()分配缓冲区，将缓冲区建立在物理内存中。可以提高效率。
  进行分配和取消分配所需成本通常高于非直接缓冲区。
  内容可以驻留在常规的垃圾回收堆外，对应用程序内存需求量的影响不明显。
  建立分配给那些易受基础系统的本机IO操作影响 的大型、持久缓冲区。
  能提高程序性能时分配直接缓冲区
  弊端：不安全、消耗大、不能直接释放资源（交由操作系统控制。等待垃圾回收器清除，System.gc()可以加快垃圾回收机制回收，但是依然不确定会立即回收）
  可以通过FileChannel.map()将文件区域直接映射到内存中创建直接缓冲区。此方法返回MappedByteBuffer。
  若直接缓冲区有不可访问的内存区域，会在某个时间抛出异常。
  isDirect()判断是否是直接缓冲区
通道 Channel 源节点与目标节点的连接。NIO中负责缓存区数据的传输，本身不存储数据
  FileChannel
  SocketChannel 用于socket(tcp)
  ServerSocketChannel
  DatagramChannel 用于dcp
获取通道
  支持通道的类.getChannel();
  本地IO
    FileInputStream()/FileOutputStream()
    RandomAccessFile();
  网络IO
    Socket
    ServerSocket
    DatagramSocket
  jdk7 NIO.2对各个通道提供了类.open()
  jdk7 NIO.2的Files.newByteChannel()
通道间数据传输
  transferFrom()
  transferTo()
分散读Scatter：从Channel中读取的数据分散到多个buffer
        依次填满buffer
聚集写Gather：多个缓冲区的数据依次写入channel

 Charset
 编码：字符串——》字节数组
 解码：字节数组——》字符串

网络NIO
    通道-
        SelectableChannel
            |--SocketChannel
            |--ServerSocketChannel
            |--DatagramChannel
            |--Pipe.SinkChannel
            |--Pipe.SocketChannel

    缓冲区
    选择器Selector 是SelectableChannel的多路复用器，用于监控SelectableChannel的IO状况
 */
public class TestNIO {

    @Test
    public void testPipe() throws IOException {
        var pipe = Pipe.open();

        var sinkChannel = pipe.sink();
        //发送数据
        var buffer = ByteBuffer.allocate(1024);
        buffer.put("通过单向管道发送数据".getBytes());
        buffer.flip();
        sinkChannel.write(buffer);
        //接收数据
        var sourceChannel = pipe.source();
        buffer.flip();
        int len = sourceChannel.read(buffer);
        System.out.println(new String(buffer.array(), 0, len));
        sourceChannel.close();
        sinkChannel.close();
    }


    /*DCPSendTest*/
    @Test
    public void testDCPSend() throws IOException {

    }

    @Test
    public void testDCPReceive() throws IOException {
        var datagramChannel = DatagramChannel.open();
        datagramChannel.configureBlocking(false);
        datagramChannel.bind(new InetSocketAddress(10089));
        var selector = Selector.open();
        datagramChannel.register(selector, SelectionKey.OP_READ);
        while (selector.select() > 0) {
            selector.selectedKeys().forEach(selectionKey -> {
                if (selectionKey.isReadable()) {
                    var buffer = ByteBuffer.allocateDirect(1024);
                    try {

                        datagramChannel.receive(buffer);
                        buffer.flip();
                        var bytes = new byte[buffer.limit()];
                        buffer.get(bytes);
                        System.out.println(new String(bytes));
                        buffer.clear();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
                selector.selectedKeys().remove(selectionKey);
            });
        }
    }


    /*非阻塞式客户端:无法在单元测试的控制台输入数据，所以代码放在类SocketClientTest*/
    @Test
    public void testSocketClient3() throws IOException {

    }

    /*非阻塞式服务端,使用迭代器循环选择器55*/
    @Test
    public void testSocketServer4() {
        try {


            //获取通道
            var channel = ServerSocketChannel.open();
            //切换成非阻塞模式
            channel.configureBlocking(false);
            //绑定ip
            channel.bind(new InetSocketAddress("localhost", 10089));
            //通道注册到选择器,监听连接事件
            var selector = Selector.open();
            channel.register(selector, SelectionKey.OP_ACCEPT);
            //
            Boolean isActivity = true;
            while (isActivity) {
                //多路复用器开始监听
                selector.select();
                var iterator = selector.selectedKeys().iterator();
                while (iterator.hasNext()) {
                    //获取准备就绪的事件
                    var key = iterator.next();
                    if (key.isAcceptable()) {
                        //连接事件
                        var connect = ((ServerSocketChannel) key.channel()).accept().configureBlocking(false);
                        //连接注册读监听
                        connect.register(selector, SelectionKey.OP_READ);
                    } else if (key.isReadable()) {
                        //读连接
                        var connect = (SocketChannel) key.channel();
                        //读取客户端数据
                        var buffer = ByteBuffer.allocate(1024);
                        int len = 0;
                        while ((len = connect.read(buffer)) != 0) {
                            buffer.flip();
                            System.out.println(new String(buffer.array(), 0, len));
                            buffer.clear();
                        }
                        //响应 <meta charset="UTF-8" />
                        var result = """
                                HTTP/1.1 200 OK
                                                
                                <html>
                                <head></head>
                                <body>兄弟，看到没！</body>
                                </html>
                                """;
                    /*buffer.put(result.getBytes());
                    buffer.flip();*/
                        connect.write(Charset.forName("utf-8").encode(result));
//                        buffer.clear();
                        connect.close();
                    }
                    //取消选择建
                    iterator.remove();
                }
            }
        } catch (ClosedChannelException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        //发送反馈给客户端
 /*       buffer.put("服务端接收数据成功".getBytes());
        buffer.flip();
        socketChannel.write(buffer);
        //关闭通道
        channel.close();
        fileChannel.close();
        socketChannel.close();*/
    }

    /*非阻塞式服务端,for循环选择器*/
    @Test
    public void testSocketServer3() throws IOException {
        //获取通道
        var channel = ServerSocketChannel.open();
        //切换成非阻塞模式
        channel.configureBlocking(false);
        //绑定ip
        channel.bind(new InetSocketAddress("localhost", 10089));

        //获取选择器
        var selector = Selector.open();
        //通道注册到选择器,指定监听事件
        channel.register(selector, SelectionKey.OP_ACCEPT);


        //轮询获取选择器上已经准备就绪的事件
        while (selector.select() > 0) {
            selector.selectedKeys().forEach(selectionKey -> {
                if (selectionKey.isAcceptable()) {
                    try {
                        var socketChannel = channel.accept();
                        //切换非阻塞模式
                        socketChannel.configureBlocking(false);
                        //将该通道注册到选择器上
                        socketChannel.register(selector, SelectionKey.OP_READ);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else if (selectionKey.isReadable()) {
                    SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
                    //接收客户端数据，并保存
                    var buffer = ByteBuffer.allocate(1024);
                    try {
                        int len = 0;
                        while ((len = socketChannel.read(buffer)) > 0) {
                            buffer.flip();
                            System.out.println(new String(buffer.array(), 0, len));
                            buffer.clear();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                //取消选择建
                selector.selectedKeys().remove(selectionKey);
            });
        }


        //发送反馈给客户端
 /*       buffer.put("服务端接收数据成功".getBytes());
        buffer.flip();
        socketChannel.write(buffer);
        //关闭通道
        channel.close();
        fileChannel.close();
        socketChannel.close();*/
    }


    /*阻塞式客户端*/
    @Test
    public void testSocketClient2() throws IOException {
        //获取通道
        var channel = SocketChannel.open(new InetSocketAddress("127.0.0.1", 10089));
        //读取文件，发送到服务器
        var buffer = ByteBuffer.allocate(1024);
        var fileChannel = FileChannel.open(Paths.get("girl1.png"), StandardOpenOption.READ);
        while (fileChannel.read(buffer) != -1) {
            buffer.flip();
            channel.write(buffer);
            buffer.clear();
        }

        channel.shutdownOutput();

        //接收服务端反馈
        int len = 0;
        while ((len = channel.read(buffer)) != -1) {
            buffer.flip();
            System.out.println(new String(buffer.array(), 0, len));
            buffer.clear();
        }
        //关闭通道
        channel.close();
        fileChannel.close();
    }

    /*阻塞式服务端*/
    @Test
    public void testSocketServer2() throws IOException {
        //获取通道
        var channel = ServerSocketChannel.open();
        //绑定连接
        channel.bind(new InetSocketAddress(10089));
        //获取客户端连接的通道
        var socketChannel = channel.accept();

        //接收客户端数据，并保存
        var buffer = ByteBuffer.allocate(1024);
        var fileChannel = FileChannel.open(Paths.get("a.png"), StandardOpenOption.WRITE, StandardOpenOption.CREATE);
        while (socketChannel.read(buffer) != -1) {
            buffer.flip();
            fileChannel.write(buffer);
            buffer.clear();
        }
        //发送反馈给客户端
        buffer.put("服务端接收数据成功".getBytes());
        buffer.flip();
        socketChannel.write(buffer);
        //关闭通道
        channel.close();
        fileChannel.close();
        socketChannel.close();
    }

    /*阻塞式客户端*/
    @Test
    public void testSocketClient() throws IOException {
        //获取通道
        var channel = SocketChannel.open(new InetSocketAddress("127.0.0.1", 10089));
        var fileChannel = FileChannel.open(Paths.get("girl1.png"));
        //分配指定大小的缓冲区
        var buffer = ByteBuffer.allocate(1024);
        //读取文件，发送到服务器
        while (fileChannel.read(buffer) != -1) {
            buffer.flip();
            channel.write(buffer);
            buffer.clear();
        }
        //关闭通道
        channel.close();
        fileChannel.close();
    }

    /*阻塞式服务端*/
    @Test
    public void testSocketServer() throws IOException {
        //获取通道
        var channel = ServerSocketChannel.open();
        var fileChannel = FileChannel.open(Paths.get("a.png"), StandardOpenOption.WRITE, StandardOpenOption.CREATE);
        //绑定连接
        channel.bind(new InetSocketAddress(10089));
        //获取客户端连接的通道
        var socketChannel = channel.accept();

        //分配指定大小的缓冲区
        var buffer = ByteBuffer.allocate(1024);
        //接收客户端数据，并保存
        while (socketChannel.read(buffer) != -1) {
            buffer.flip();
            fileChannel.write(buffer);
            buffer.clear();
        }
        //关闭通道
        channel.close();
        fileChannel.close();
        socketChannel.close();
    }

    @Test
    public void testCharset() throws CharacterCodingException, UnsupportedEncodingException {
//        Charset.availableCharsets().keySet().forEach(key-> System.out.println(key));

        var charset = Charset.forName("UTF-8");
        //获取编码器
        var encoder = charset.newEncoder();
        //获取解码器
        var decoder = charset.newDecoder();

        var buffer = CharBuffer.allocate(1024);
        buffer.put("ni好吗，我是陈志源");


        //编码
        buffer.flip();
        var encodeBuffer = encoder.encode(buffer);
        System.out.println(new String(encodeBuffer.array(), "UTF-8"));

        //解码
        var decodeBuffer = decoder.decode(encodeBuffer);
        System.out.println(decodeBuffer.toString());
    }

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
        var fileInputStream = new FileInputStream(FileUtilOld.getResourceFile("girl.png"));
        var file = FileUtilOld.getResourceFile("2.png");
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
        var outChannel = FileChannel.open(Paths.get("4.png"), StandardOpenOption.WRITE, StandardOpenOption.READ, StandardOpenOption.CREATE);

        //内存映射文件
        var inMappedBuffer = inChannel.map(FileChannel.MapMode.READ_ONLY, 0, inChannel.size());
        var outMappedBuffer = outChannel.map(FileChannel.MapMode.READ_WRITE, 0, inChannel.size());
        //直接读写缓冲区
        var dst = new byte[inMappedBuffer.limit()];

        inMappedBuffer.get(dst);
        outMappedBuffer.put(dst);

        inChannel.close();
        outChannel.close();
    }


    /*直接缓冲区完成文件的复制:通道间数据传输*/
    @Test
    public void testChannel3() throws IOException {
        var inChannel = FileChannel.open(Paths.get("girl1.png"), StandardOpenOption.READ);
        var outChannel = FileChannel.open(Paths.get("4.png"), StandardOpenOption.WRITE, StandardOpenOption.READ, StandardOpenOption.CREATE);
        inChannel.transferTo(0, inChannel.size(), outChannel);
        inChannel.close();
        outChannel.close();
    }

    /*直接缓冲区完成文件的复制:通道间数据传输*/
    @Test
    public void testChannel4() throws IOException {
        var inChannel = FileChannel.open(Paths.get("girl1.png"), StandardOpenOption.READ);
        var outChannel = FileChannel.open(Paths.get("4.png"), StandardOpenOption.WRITE, StandardOpenOption.READ, StandardOpenOption.CREATE);
        outChannel.transferFrom(inChannel, 0, inChannel.size());
        inChannel.close();
        outChannel.close();
    }

    /*分散与聚聚*/
    @Test
    public void testChannel5() throws IOException {
        var accessFile = new RandomAccessFile("a.text", "rw");
        var channel = accessFile.getChannel();
        var buffer = ByteBuffer.allocate(100);
        var buffer2 = ByteBuffer.allocate(1024);

        //分散读取
        ByteBuffer[] byteBuffers = {buffer, buffer2};
        channel.read(byteBuffers);
        System.out.println(new String(byteBuffers[0].array(), 0, byteBuffers[0].limit()));
        System.out.println(new String(byteBuffers[1].array(), 0, byteBuffers[1].limit()));


        //聚集写入
        var accessFile2 = new RandomAccessFile("b.text", "rw");
        var channel2 = accessFile2.getChannel();
        for (var b : byteBuffers) {
            b.flip();
        }
        channel2.write(byteBuffers);


    }
}
