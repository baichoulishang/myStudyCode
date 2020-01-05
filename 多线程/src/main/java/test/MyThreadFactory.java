package test;

import java.util.concurrent.ThreadFactory;

/**
 * @author 陈宜康
 * @date 2019/10/5 10:17
 * @forWhat
 */
public class MyThreadFactory implements ThreadFactory {
    @Override
    public Thread newThread(Runnable r) {
        Thread thread = new Thread(r);
        return thread;
    }
}
