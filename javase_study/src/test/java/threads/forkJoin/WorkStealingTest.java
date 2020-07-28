package threads.forkJoin;

import com.czy.util.time.TimeUtil;

import java.util.concurrent.Executors;

/**
 * @author chenzy
 * @date 2020-07-28
 */
public class WorkStealingTest {
    public static void main(String[] args) {
        //充分利用CPU资源,提高并行度
        var pool=Executors.newWorkStealingPool();
        for (int i = 0; i < 10; i++) {
            final int count =i;
            pool.submit(()->{
                System.out.println("线程:"+Thread.currentThread()+"完成任务"+count+"     时间:"+ TimeUtil.nowStr());
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
    }
}
