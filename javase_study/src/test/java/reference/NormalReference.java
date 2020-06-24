package reference;

import java.io.IOException;

/**
 * @author chenzy
 * @date 2020-06-24
 * 强引用
 * 当对象没有被引用时，垃圾回收机制会回收对象。
 * 调用System.gc()加快垃圾回收，但不代表立即回收
 * 对象被回收后会执行对象的finalize方法。
 */
public class NormalReference {
    public static void main(String[] args) throws IOException {
        var m = new M();
        m=null;
        System.gc();
        System.in.read();//阻塞main线程，给垃圾回收线程时间执行
    }

}
