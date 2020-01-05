package test;

import java.util.HashMap;

/**
 * @author 陈宜康
 * @date 2019/10/4 19:12
 * @forWhat
 */
public class XAbstractTask implements Runnable {
    // 线程局部变量
    static ThreadLocal<HashMap<String, String>> config = new ThreadLocal<HashMap<String, String>>() {
        protected HashMap<String, String> initialValue() {
            return new HashMap<String, String>();
        }
    };

    // 清空线程特有对象的状态
    protected void clear() {
        config.get().clear();
    }

    // 暴露给子类,用来实现具体的任务逻辑
    protected void mainRun() {

    }

    @Override
    public void run() {
        try {
            // 先清空状态,再执行任务
            clear();
            mainRun();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
