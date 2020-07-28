package threads.forkJoin;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.TimeUnit;

/**
 * @author chenzy
 * @date 2020-07-28
 */
public class PrintTask extends RecursiveAction {
    private static final int THRESHOLD=50;//最多只能打印50个数
    private int start;
    private int end;

    public PrintTask(int start, int end) {
        this.start = start;
        this.end = end;
    }

    @Override
    protected void compute() {
        var length = end - start;
        if (length < THRESHOLD) {
            for (int i = start; i < end; i++){
                System.out.println(Thread.currentThread().getName()+"的i值："+i);
            }
        }else {
            int middle = (start+end)/2;
            var left = new PrintTask(start,middle);
            var right = new PrintTask(middle,end);
            left.fork();
            right.fork();
        }
    }
    public static void main(String[] args) throws InterruptedException {
        var task=new PrintTask(0,300);
        var pool=new ForkJoinPool();
        pool.invoke(task);
        pool.submit(task);
        pool.awaitTermination(2, TimeUnit.SECONDS);//线程阻塞
        pool.shutdown();
    }
}
