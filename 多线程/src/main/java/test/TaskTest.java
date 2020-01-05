package test;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author 陈宜康
 * @date 2019/10/5 18:16
 * @forWhat
 */
public class TaskTest {
    final static int N_CPU = Runtime.getRuntime().availableProcessors();
    static final ThreadPoolExecutor executor = new ThreadPoolExecutor(
            2,
            N_CPU * 2,
            4,
            TimeUnit.SECONDS,
            new ArrayBlockingQueue<Runnable>(N_CPU * 8),
            new ThreadPoolExecutor.CallerRunsPolicy());

    // public static void main(String[] args) throws Exception {
    //     Role role = new Role();
    //     System.out.println("值是:" + role.getAge());
    // }

    public static void main(String[] args) throws Exception {
        String check = "abc";
        String str1 = null;
        try {
            str1 = new StringBuilder(" 计算机").append("软件").toString();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }
        System.out.println(str1.intern() == str1);
        String str2 = new StringBuilder("ja").append("va").toString();
        System.out.println(str2.intern() == str2);
    }

    /**
     * 测试报错
     *
     * @param
     * @return void
     * @author CYK
     * @Date 7:51 2019/10/6
     **/
    public static void testException() {
        myRunnable runnable = new myRunnable();
        uncaughtExe exe = new uncaughtExe();
        Thread thread = new Thread(runnable);
        thread.setUncaughtExceptionHandler(exe);
        thread.start();
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
            System.out.println("出错的任务的运行线程:" + Thread.currentThread().getName());
            int i = 1 / 0;
            System.out.println("任务");
        }
    }

    static class uncaughtExe implements Thread.UncaughtExceptionHandler {
        @Override
        public void uncaughtException(Thread t, Throwable e) {
            System.out.println("当前线程名字:" + t.getName());
            try {
                System.out.println("捕获到了未抛出的异常:");
                throw e;
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
        }
    }

}
