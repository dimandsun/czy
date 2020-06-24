package reference;

import java.io.IOException;
import java.lang.ref.SoftReference;

/**
 * @author chenzy
 * @date 2020-06-24
 * 软引用
 * 软引用对象不会被垃圾回收掉
 * 软引用在内存够时不会被回收，内存不够时回收
 * 软引用非常适合做缓存
 */
public class MySoftReference {
    public static void main(String[] args) throws IOException {
        //m强引用指向SoftReference对象，SoftReference对象软引用指向10m的字节数组
        var m= new SoftReference<>(new byte[1024 * 1024 * 10]);//10M
        System.out.println(m.get());
        System.gc();//
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(m.get());
        var b=new byte[1024 * 1024 * 15];
        System.out.println(m.get());
    }

}
