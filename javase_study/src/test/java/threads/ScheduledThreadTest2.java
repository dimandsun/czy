package threads;

import com.czy.util.time.TimeUtil;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author chenzy
 * @date 2020-07-28
 */
public class ScheduledThreadTest2 {
    public static void main(String[] args) {
        //corePoolSize为线程数
        var executorService= Executors.newScheduledThreadPool(5);
        System.out.println(TimeUtil.nowStr());
        executorService.scheduleAtFixedRate(()->{
            System.out.println("1---------------延迟一秒执行,每三秒执行一次");
            System.out.println("1---------------"+TimeUtil.nowStr());
        },1,3,TimeUnit.SECONDS);
        executorService.scheduleAtFixedRate(() -> {
            System.out.println("2---------------延迟一秒执行,每三秒执行一次");
            System.out.println("2---------------"+TimeUtil.nowStr());
        },1,3, TimeUnit.SECONDS);
        executorService.scheduleAtFixedRate(() -> {
            System.out.println("3---------------延迟一秒执行,每三秒执行一次");
            System.out.println("3---------------"+TimeUtil.nowStr());
        },1,3, TimeUnit.SECONDS);
        executorService.scheduleAtFixedRate(() -> {
            System.out.println("4---------------延迟一秒执行,每三秒执行一次");
            System.out.println("4---------------"+TimeUtil.nowStr());
        },1,3, TimeUnit.SECONDS);
    }
}
