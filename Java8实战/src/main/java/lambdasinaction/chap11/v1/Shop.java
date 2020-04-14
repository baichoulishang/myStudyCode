package lambdasinaction.chap11.v1;

import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

import static lambdasinaction.chap11.Util.delay;

public class Shop {

    private final String name;
    private final Random random;

    public Shop(String name) {
        this.name = name;
        random = new Random(name.charAt(0) * name.charAt(1) * name.charAt(2));
    }

    public double getPrice(String product) {
        return calculatePrice(product);
    }

    private double calculatePrice(String product) {
        delay();
        return random.nextDouble() * product.charAt(0) + product.charAt(1);
    }

    public Future<Double> getPriceAsync(String product) {
        // 创建CompletableFuture对象,他会包含计算结果
        CompletableFuture<Double> futurePrice = new CompletableFuture<>();
        // 在另一个线程中以异步方式执行计算
        new Thread(() -> {
            double price = calculatePrice(product);
            futurePrice.complete(price);// 需长时间计算的任务结束并得出结果时，设置Future的返回值
        }).start();
        return futurePrice;// 直接返回Future对象.我们可以从Future中获取处理结果
    }

    public String getName() {
        return name;
    }

}
