package threads;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author chenzy
 * @date 2020-07-28
 */
public class ScheduledThreadTest {
    public static void main(String[] args) {
        var executorService= Executors.newScheduledThreadPool(5);
        System.out.println(System.currentTimeMillis());
        executorService.schedule(() -> {
            System.out.println("延迟三秒执行");
            System.out.println(System.currentTimeMillis());
        },3, TimeUnit.SECONDS);//延迟三秒执行
    }
}
