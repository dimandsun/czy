package reference;

import java.util.concurrent.TimeUnit;

/**
 * @author chenzy
 * @date 2020-06-24
 * ThreadLocal：线程绑定，线程隔离
 * 每个线程都有个map，通过往线程map赋值隔离线程。
 * 往线程map赋值时，会new一个实现WeakReference弱引用的内部entry
 */
public class MyThreadLocal {
    //threadLocal指向ThreadLocal对象
    static ThreadLocal<Person> threadLocal = new ThreadLocal<>();
    public static void main(String[] args) {
        new Thread(()->{
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(threadLocal.get());
        }).start();

        new Thread(()->{
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            threadLocal.set(new Person());
        }).start();


    }
    static class Person{
        String name="张三";
    }
}
