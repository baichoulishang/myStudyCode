package lambdasinaction.chap7;

import redis.clients.jedis.Jedis;

/**
 * @date 2020/3/29 14:57
 */
public class PipelineExample {
    // public static void main(String[] args) {
    //     Jedis jedis = new Jedis("127.0.0.1", 6379);
    //     // 记录执行开始时间
    //     long beginTime = System.currentTimeMillis();
    //     // 获取 Pipeline 对象
    //     Pipeline pipe = jedis.pipelined();
    //     // 设置多个 Redis 命令
    //     for (int i = 0; i < 100; i++) {
    //         pipe.set("key" + i, "val" + i);
    //         pipe.del("key" + i);
    //     }
    //     // 执行命令
    //     pipe.sync();
    //     // 记录执行结束时间
    //     long endTime = System.currentTimeMillis();
    //     System.out.println("执行耗时：" + (endTime - beginTime) + "毫秒");
    // }
    public static void main(String[] args) {
        Jedis jedis = new Jedis("127.0.0.1", 6379);
        // 记录执行开始时间
        long beginTime = System.currentTimeMillis();
        for (int i = 0; i < 100; i++) {
            jedis.set("key" + i, "val" + i);
            jedis.del("key" + i);
        }
        // 记录执行结束时间
        long endTime = System.currentTimeMillis();
        System.out.println("执行耗时：" + (endTime - beginTime) + "毫秒");
    }
}
