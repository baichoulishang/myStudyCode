package test;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class Test {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        CompletableFuture<Double> futurePrice = new CompletableFuture<>();
        System.out.println(Thread.currentThread().getName());
        new Thread(() -> {
            double ok = 0;
            try {
                ok = 1 / 0;
            } catch (Exception e) {
                System.out.println("报错的线程是:" + Thread.currentThread().getName());
                e.printStackTrace();
            }
            futurePrice.complete(ok);
        }).start();
        futurePrice.get();
        System.out.println("结束了");
    }
}
