package test;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author 陈宜康
 * @date 2019/10/3 15:37
 * @forWhat
 */
public class ConditionUsage {
    private final Lock lock = new ReentrantLock();
    private final Condition condition = lock.newCondition();

    // public void method() {
    //     lock.lock();
    //     try {
    //         while (保护条件不成立) {
    //             condition.await();
    //         }
    //         doAction();
    //     } catch (InterruptedException e) {
    //         e.printStackTrace();
    //     } finally {
    //         lock.unlock();
    //     }
    // }
    // private void doAction() {
    //
    // }

    // public void notifyMethod() {
    //     lock.lock();
    //     try {
    //         // 更新共享变量
    //         changeState();
    //         condition.signal();
    //     } catch (Exception e) {
    //         e.printStackTrace();
    //     } finally {
    //         lock.unlock();
    //     }
    // }
}
