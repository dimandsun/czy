package nio;

import org.junit.Test;

import java.nio.*;

/**
 * @author chenzy
 * @date 2020-08-04
 */

/*
缓冲区：buffer 在NIO中负责数据的存取。
        数组，用于存储不同数据类型的数据。除boolean外的基本数据类型都有对应的Buffer
    ByteBuffer
    CharBuffer
    ShortBuffer
    IntBuffer
    LongBuffer
    FloatBuffer
    DoubleBuffer
Buffer中四个属性
  capacity：容量，最大存储容量，声明后不能改变
  limit：   限制，可读写数据大小，limit后的数据不能读写
  position  位置，正在读写数据的位置
  mark      标记，记录当前position，通过reset恢复position到mark记录值
  0<=mark<=position<=limit<=capacity
创建： Buffer.wrap
      ByteBuffer.allocate
      ByteBuffer.allocateDirect
常用方法：
    position();
    limit();
    mark();
    capacity();
    position(index);
    limit(index);
    reset();position变为mark值

    put(value);
    put(index,value);
    get();
    get(index);

    flip();//limit=position;position=0;mark=-1;//缩小limit范围，重新读写，清除标记
    clear();//limit=capacity;position=0;mark=-1;//重置缓冲区
    rewind();//position=0;mark=-1;//重新读写，清除标记

    slice();//从position截取一个新的缓冲区，两个缓冲区共用一个数组,但是四个属性独立
    arrayOffset();//第一个缓冲元素的偏移量，使用slice()得到的缓冲区的arrayOffset可能不为0

    duplicate();//复制缓冲区，共享数组，但是四个属性独立

    asReadOnlyBuffer();

    compact();//已读的数据覆盖掉，未读的数据作为已读的数据。使用compact后可用flip来读取数据

 */
public class BufferTest {
    @Test
    public void bufferTest() {
        {
            var byteBuffer = ByteBuffer.wrap(new byte[]{1, 2, 3, 4});
            var shortBuffer = ShortBuffer.wrap(new short[]{1, 2, 3, 4});
            var intBuffer = IntBuffer.wrap(new int[]{1, 2, 3, 4});
            var longBuffer = LongBuffer.wrap(new long[]{1, 2, 3, 4});
            var floatBuffer = FloatBuffer.wrap(new float[]{1, 2, 3, 4});
            var doubleBuffer = DoubleBuffer.wrap(new double[]{1, 2, 3, 4});
            var charBuffer = CharBuffer.wrap(new char[]{1, 2, 3, 4});
            charBuffer.arrayOffset();
            System.out.println(byteBuffer);
            byteBuffer.put((byte) 5);
            byteBuffer.put((byte) 5);
            byteBuffer.put((byte) 5);
            System.out.println(byteBuffer);
            System.out.println(byteBuffer.remaining());
            System.out.println(byteBuffer.hasRemaining());
        }
        {
            //直接缓冲区
            ByteBuffer.allocateDirect(2);
        }

    }


    @Test
    public void testFlip() {
        var byteBuffer = ByteBuffer.allocate(3);
        byteBuffer.put((byte) 'a');
        byteBuffer.flip();
        System.out.println(byteBuffer);
        System.out.println(byteBuffer.get());
        System.out.println(byteBuffer);
        byteBuffer.clear();
        System.out.println(byteBuffer);
    }
    @Test
    public void testSlice() {
        var byteBuffer = ByteBuffer.allocate(3);
        byteBuffer.put((byte) 'a');
        var newBuffer = byteBuffer.slice();
        System.out.println(byteBuffer);
        System.out.println(newBuffer);
        System.out.println(newBuffer.arrayOffset());
    }

    @Test
    public void testReadOnly() {

        var byteBuffer = ByteBuffer.allocate(3);
        var readOnlyBuffer=byteBuffer.asReadOnlyBuffer();
    }

    @Test
    public void testCompact() {
        var byteBuffer = ByteBuffer.allocate(3);
        byteBuffer.put((byte) '1');
        System.out.println(byteBuffer);
        byteBuffer.compact();
        System.out.println(byteBuffer);
        while (byteBuffer.hasRemaining()){
            System.out.println(byteBuffer.get());
        }
    }

    @Test
    public void testDuplicate() {
        var byteBuffer = ByteBuffer.allocate(3);
        byteBuffer.duplicate();
    }
}