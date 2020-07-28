package threads;

import java.util.concurrent.Executors;

/**
 * @author chenzy
 * @date 2020-07-28
 */
public class SingleThreadTest {
    public static void main(String[] args) {
        var executorService= Executors.newSingleThreadExecutor();
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
