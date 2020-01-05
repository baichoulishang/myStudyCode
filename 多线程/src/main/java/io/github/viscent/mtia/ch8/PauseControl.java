/*
授权声明：
本源码系《Java多线程编程实战指南（核心篇）》一书（ISBN：978-7-121-31065-2，以下称之为“原书”）的配套源码，
欲了解本代码的更多细节，请参考原书。
本代码仅为原书的配套说明之用，并不附带任何承诺（如质量保证和收益）。
以任何形式将本代码之部分或者全部用于营利性用途需经版权人书面同意。
将本代码之部分或者全部用于非营利性用途需要在代码中保留本声明。
任何对本代码的修改需在代码中以注释的形式注明修改人、修改时间以及修改内容。
本代码可以从以下网址下载：
https://github.com/Viscent/javamtia
http://www.broadview.com.cn/31065
*/
package io.github.viscent.mtia.ch8;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class PauseControl extends ReentrantLock {
    private static final long serialVersionUID = 176912639934052187L;
    // 设置条件变量,用来暂停和唤醒线程
    private final Condition condSuspended = newCondition();
    // 线程暂挂标志
    private volatile boolean suspended = false;

    /**
     * 暂停线程
     */
    public void requestPause() {
        suspended = true;
    }

    /**
     * 恢复线程
     */
    public void proceed() {
        // 以当前对象为锁实例,申请锁
        // 原子操作
        lock();
        try {
            suspended = false;
            // 唤醒所有的等待线程
            condSuspended.signalAll();
        } finally {
            // 以当前对象为锁实例,解锁
            unlock();
        }
    }

    /**
     * 当前线程仅在线程暂挂标记不为true的情况下才执行指定的目标动作。
     *
     * @throws InterruptedException
     * @targetAction 目标动作
     */
    public void pauseIfNeccessary(Runnable targetAction) throws InterruptedException {
        // 三个条件,1,循环;2,await语句;3,释放锁的语句放在finally中
        lock();
        try {
            while (suspended) {
                condSuspended.await();
            }
            targetAction.run();
        } finally {
            unlock();
        }
    }
}