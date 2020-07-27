package threads;

import com.czy.util.text.StringUtil;

import java.util.concurrent.Executors;

/**
 * @author chenzy
 * @date 2020-07-27
 * 创建一个定长线程池，可控制线程最大并发数，超出的线程会在队列中等待
 * 定长线程池的大小最好根据系统资源进行设置。如Runtime.getRuntime().availableProcessors()
 */
public class FixedThreadTest {
    public static void main(String[] args) {
        var executorService= Executors.newCachedThreadPool();
        for (int i = 0; i < 20; i++) {
            executorService.execute(()->{
                try {
                    StringUtil.println()
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
