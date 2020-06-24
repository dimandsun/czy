package reference;

import java.lang.ref.PhantomReference;
import java.lang.ref.ReferenceQueue;
import java.util.LinkedList;
import java.util.List;

/**
 * @author chenzy
 * @date 2020-06-24
 * 虚引用.JVM内部使用的
 * 管理直接内存。
 * NIO中引入的直接内存，即操作系统的内存。
 * 普通对象存放在JVM内存中，
 */
public class MyPhantomReference {
    private static final List<Object> LIST=new LinkedList<>();
    //队列
    private static final ReferenceQueue<M> QUEUE=new ReferenceQueue<>();

    public static void main(String[] args) {
        //phantomReference强引用指向PhantomReference对象，PhantomReference对象虚引用指向M对象
        //垃圾回收时，仅起一个通知作用。通知虚引用把M对象放到QUEUE
       var phantomReference= new PhantomReference<>(new M(), QUEUE);
        System.out.println("1："+phantomReference.get());
        new Thread(()->{
            while (true){
                LIST.add(new byte[1024*1024]);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    Thread.currentThread().interrupt();
                }
                System.out.println("a："+phantomReference.get());
            }
        }).start();
        new Thread(()->{
            while (true){
                var poll = QUEUE.poll();
                if (poll!=null){
                    System.out.println("------虚引用对象被jvm回收了---------------"+poll);
                }
            }
        }).start();
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
