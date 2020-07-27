package threads;

import java.util.concurrent.Executors;

/**
 * @author chenzy
 * @date 2020-07-27
 * 创建一个可缓存线程池，如果线程池长度超过处理需要，可灵活回收空闲线程，若无可回收，则新建线程
 * 线程池为无限大，当执行第二个任务时第一个任务已经完成，会复用执行第一个任务的线程，而不用每次新建线程
 */
public class CachedThreadTest {
    public static void main(String[] args) {
        var executorService= Executors.newCachedThreadPool();
        for (int i = 0; i < 20; i++) {
            executorService.execute(()->{
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName()+"----------running");
            });
        }
        executorService.shutdown();
    }
}
