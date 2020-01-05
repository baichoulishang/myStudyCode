package test;

import java.util.Comparator;
import java.util.concurrent.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author 陈宜康
 * @date 2019/10/1 21:06
 * @forWhat
 */
public class MyTest implements Comparator, Comparable {


    final static int N_CPU = Runtime.getRuntime().availableProcessors();
    static final ThreadPoolExecutor executor = new ThreadPoolExecutor(
            2,
            N_CPU * 2,
            4,
            TimeUnit.SECONDS,
            new ArrayBlockingQueue<Runnable>(N_CPU * 8),
            new ThreadPoolExecutor.CallerRunsPolicy());
    private static final Lock lock = new ReentrantLock();

    public static void main(String[] args) throws ExecutionException, InterruptedException {
    }

    public static void fuckRole(Role role) {
        role.setId("111");
    }

    public static void tryLock() {
        if (lock.tryLock()) {
            System.out.println("申请锁成功");
            try {

            } finally {
                lock.unlock();
            }
        } else {
            System.out.println("执行了其他");
        }
    }

    @Override
    public int compareTo(Object o) {
        return 0;
    }

    @Override
    public int compare(Object o1, Object o2) {
        return 0;
    }

    public void check() {
        synchronized (this) {
            System.out.println("同步");

        }
    }

    static class MyCallable implements Callable<Role> {
        @Override
        public Role call() throws Exception {
            Role role = new Role();
            role.setRolename("不堪寂寞");
            return role;
        }
    }

    static class myRunnable implements Runnable {
        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName());
            System.out.println("任务");
        }
    }

    static class uncaughtExe implements Thread.UncaughtExceptionHandler {
        @Override
        public void uncaughtException(Thread t, Throwable e) {
            System.out.println(t.getName());
            System.out.println("捕获到了异常");
        }
    }

}
