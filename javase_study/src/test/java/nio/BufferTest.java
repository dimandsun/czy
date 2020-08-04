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
 */
public class BufferTest {
    @Test public void bufferTest(){
        var byteBuffer=ByteBuffer.wrap(new byte[]{1,2,3,4});
        var shortBuffer= ShortBuffer.wrap(new short[]{1,2,3,4});
        var intBuffer= IntBuffer.wrap(new int[]{1,2,3,4});
        var longBuffer= LongBuffer.wrap(new long[]{1,2,3,4});
        var floatBuffer= FloatBuffer.wrap(new float[]{1,2,3,4});
        var doubleBuffer= DoubleBuffer.wrap(new double[]{1,2,3,4});
        var charBuffer= CharBuffer.wrap(new char[]{1,2,3,4});
    }

}
