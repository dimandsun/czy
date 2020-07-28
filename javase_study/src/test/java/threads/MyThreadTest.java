package threads;

import java.util.Collection;
import java.util.Iterator;
import java.util.concurrent.*;

/**
 * @author chenzy
 * @date 2020-07-28
 */
public class MyThreadTest {
    public static void main(String[] args) {
        /*corePoolSize:      核心线程数量（）
         maximumPoolSize：   最大线程数
         keepAliveTime：     存活时间
         unit：              存活时间的单位
         workQueue：         保存任务的阻塞队列
            -BlockingQueue:阻塞队列：子类存储结构上或对元素操作上的不同，但是对于take与put操作的原理，却是类似的。
                -SynchronousQueue:异步队列
                -ArrayBlockingQueue:
                -DelayQueue:
                -LinkedBlockingDeque:
                -PriorityBlockingQueue:
                -SynchronousQueue:
            -DelayedWorkQueue:

         threadFactory：     线程工厂，一般用默认值
         RejectedExecutionHandler：饱和策略(拒绝策略)
         */
        /*
        */
        new ThreadPoolExecutor(1, 100, 60L, TimeUnit.SECONDS,new SynchronousQueue());

    }
}
