package threads.forkJoin;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.LongStream;

/**
 * @author chenzy
 * 
 * @since 2020/6/20
 * 传统线程池
 */

/*
传统线程池弊端：
    多个cpu中的多个线程队列，当一个队列中的线程阻塞后，这个队列后面的线程就一直无法执行。无法有效利用cpu资源

线程窃取模式的Fork/Join框架：
    当一个线程队列执行完毕，会随机从其他线程队列中偷一个线程放到自己队列中。

 */
public class ForkJoinTest {
    @Before
    public void befor(){
        System.out.println(Instant.now());
    }
    @After
    public void after(){

        System.out.println(Instant.now());
    }
    @Test
    public void a() {
        var start=Instant.now();

        var pool = new ForkJoinPool();
        var task = new ForkJoinCalculate(0L, 10000000L);
        var sum=pool.invoke(task);
        System.out.println(sum);

        var end=Instant.now();
        //运行纳秒
        System.out.println(Duration.between(start,end).toMillis());
    }
    //并行流与顺序流
    //    parallel、sequential切换并行流和顺序流
    @Test
    public void parallelTest(){
        //累加.parallel()切换并行流
        var result = LongStream.rangeClosed(0,10000000L)
                .parallel()
                .reduce(0,Long::sum);
        System.out.println(result);
    }
    @Test
    public void forkJoinTest (){
        var start=Instant.now();
        Long result = 0L;
        for (Long i = 0L; i <= 10000000L; i++) {
            result+=i;
        }
        System.out.println(result);
        var end=Instant.now();
        //运行 纳秒getNano
        System.out.println(Duration.between(start,end).toMillis());
    }
}
