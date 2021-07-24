package lambdasinaction.chap7;

import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;
import java.util.stream.LongStream;

import static lambdasinaction.chap7.ParallelStreamsHarness.FORK_JOIN_POOL;

public class ForkJoinSumCalculator extends RecursiveTask<Long> {

    public static final long THRESHOLD = 10_000;// 拆分任务的尺度

    /***********子任务处理的数组的起始和终止位置****************************/
    private final long[] numbers;
    private final int start;
    private final int end;

    /***********子任务处理的数组的起始和终止位置****************************/


    // 私有构造函数用于以[递归方式]为【主任务创建子任务】
    private ForkJoinSumCalculator(long[] numbers, int start, int end) {
        this.numbers = numbers;
        this.start = start;
        this.end = end;
    }

    public ForkJoinSumCalculator(long[] numbers) {
        this(numbers, 0, numbers.length);
    }

    // 测试代码.
    public static long forkJoinSum(long n) {
        long[] numbers = LongStream.rangeClosed(1, n).toArray();
        ForkJoinTask<Long> task = new ForkJoinSumCalculator(numbers);
        return FORK_JOIN_POOL.invoke(task);
    }

    // 重写该方法以实现分支合并功能
    @Override
    protected Long compute() {
        int length = end - start;// 该任务负责求和的部分的大小
        if (length <= THRESHOLD) {// 如果大小小于或等于阈值，顺序计算结果
            return computeSequentially();
        }
        // 否则,创建一个子任务来为数组的前一半求和
        ForkJoinSumCalculator leftTask = new ForkJoinSumCalculator(numbers, start, start + length / 2);
        leftTask.fork();// 利用另一个ForkJoinPool线程异步执行新创建的子任务
        // 创建一个任务为数组的后一半求和
        ForkJoinSumCalculator rightTask = new ForkJoinSumCalculator(numbers, start + length / 2, end);
        // 得到计算结果.这里是基于异步的同步,rightResult得到结果时,rightTask及其可能被拆分的子任务都执行完了
        Long rightResult = rightTask.compute();
        Long leftResult = leftTask.join();// 暂停当前线程,并且等待leftTask结束
        return leftResult + rightResult;// 返回结果
    }

    private long computeSequentially() {
        long sum = 0;
        for (int i = start; i < end; i++) {
            sum += numbers[i];
        }
        return sum;
    }
}