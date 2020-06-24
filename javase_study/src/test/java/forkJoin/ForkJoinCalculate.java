package forkJoin;

import java.util.concurrent.RecursiveTask;

/**
 * @author chenzy
 * 
 * @since 2020/6/20
 */
public class ForkJoinCalculate extends RecursiveTask<Long> {

    private Long start;
    private Long end;
    private static final Long threshoold = 1000000l;

    public ForkJoinCalculate(Long start, Long end) {
        this.start = start;
        this.end = end;
    }

    @Override
    protected Long compute() {
        var length = end - start;
        if (length < threshoold) {
            Long sum = 0l;
            for (int i = 0; i < end; i++){
                sum += i;
            }
            return sum;
        } else {
            Long middle = (start + end) / 2;
            //拆分子任务，压入线程队列
            var left = new ForkJoinCalculate(start, middle);
            left.fork();

            var right = new ForkJoinCalculate(middle + 1, end);
            right.fork();
            return left.join() + right.join();
        }
    }
}