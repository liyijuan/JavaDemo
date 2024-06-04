package org.example.demo;

import org.redisson.Redisson;
import org.redisson.api.RBloomFilter;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

public class BoomFilterTest {
    public static void main(String[] args) {
        Config config = new Config();
        config.useSingleServer().setAddress("redis://192.168.208.124:6379").setPassword("leadnews");
        RedissonClient client = Redisson.create(config);
        RBloomFilter<String> bloomFilter = client.getBloomFilter("bloom-filter");
        initData(bloomFilter, 100);
        getData(bloomFilter, 100);
    }

    private static int getData(RBloomFilter<String> bloomFilter, int size) {
        int count = 0;
        for (int i = size; i < size * 2; i++) {
            if (bloomFilter.contains("add" + i)) {
                count++;
            }
        }
        System.out.println(count);
        return count;
    }

    private static void initData(RBloomFilter<String> bloomFilter, int size) {
//        size 指布隆过滤器内部位数组大小，0.05 期望的错误率
        bloomFilter.tryInit(size, 0.05);
        for (int x = 0; x < size; x++) {
            bloomFilter.add("add" + x);
        }
        System.out.println("初始化完成....");
    }
}
