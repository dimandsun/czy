import com.czy.log.LogFactory;
import com.czy.log.LogInfo;
import com.czy.util.io.FileUtil;
import com.czy.util.text.StringUtil;
import org.junit.Test;

import java.io.IOException;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author chenzy
 * @date 2020-07-24
 */
public class T {
    public static void main(String[] args) {

    }

    private class LogTask implements Runnable {
        @Override
        public void run() {
            System.out.println(213);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    public void ex() {
        var executor = Executors.newFixedThreadPool(123);// new ThreadPoolExecutor(4, 4, 1000, TimeUnit.SECONDS, new SynchronousQueue());
        executor.execute(() -> {
            System.out.println("saadsffdssdf"+Thread.currentThread().getName());
        });
        executor.execute(() -> {
            System.out.println("saadsffdssdf"+Thread.currentThread().getName());
        });
        executor.execute(() -> {
            System.out.println("saadsffdssdf"+Thread.currentThread().getName());
        });
        executor.execute(() -> {
            System.out.println("saadsffdssdf"+Thread.currentThread().getName());
        });
        executor.execute(() -> {
            System.out.println("saadsffdssdf"+Thread.currentThread().getName());
        });
        executor.execute(() -> {
            System.out.println("saadsffdssdf"+Thread.currentThread().getName());
        });
    }

    @Test
    public void testThread() {
        var executor = new ThreadPoolExecutor(1, 1, 10, TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(2), new MyTreadFactory(), new MyHandler());
        executor.allowCoreThreadTimeOut(true);
        for (int i = 0; i < 100; i++) {
            executor.execute(() -> {
                System.out.println("saadsffdssdf"+Thread.currentThread().getName());
            });
        }
    }
    private class MyTreadFactory implements ThreadFactory {
        private final AtomicInteger mThreadNum = new AtomicInteger(1);

        @Override
        public Thread newThread(Runnable r) {
            Thread t = new Thread(r, "日志线程-" + mThreadNum.getAndIncrement());
            System.out.println(t.getName() + " has been created");
            return t;
        }
    }
    private class MyHandler implements RejectedExecutionHandler {
        @Override
        public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
            System.err.println( r.toString() + " rejected");
        }
    }
    @Test
    public void testLog() {
        LogFactory.initLog();
        var log = LogFactory.getLog("test");
        log.info("adasdf");
        log.info("去你没得");
        log.info("浅谈JAVA中的日志文件 - CSDN博客");
        LogFactory.close();
    }

    @Test
    public void te() {
        System.out.println(StringUtil.subStr("%d{mmssSSS} [%thread] [%level]: %msg%n", "%d\\{(.*)\\}"));
    }

    @Test
    public void name() throws IOException {
        System.out.println(FileUtil.getRoot());
    }

}
