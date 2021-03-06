package com.git.hui;

import com.git.hui.redis.RedisConfig;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by yihui in 11:52 18/6/11.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {RedisConfig.class})
public class RedisTest {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Test
    public void testRedis() {
        String key = "hello";
        String value = "world";
        redisTemplate.execute((RedisCallback<Void>) con -> {
            con.set(key.getBytes(), value.getBytes());
            return null;
        });

        String asn = redisTemplate.execute((RedisCallback<String>) con -> new String(con.get(key.getBytes())));
        System.out.println(asn);


        String hkey = "hKey";
        redisTemplate.execute((RedisCallback<Void>) con -> {
            con.hSet(hkey.getBytes(), "23".getBytes(), "what".getBytes());
            return null;
        });

        Map<byte[], byte[]> map =
                redisTemplate.execute((RedisCallback<Map<byte[], byte[]>>) con -> con.hGetAll(hkey.getBytes()));
        for (Map.Entry<byte[], byte[]> entry : map.entrySet()) {
            System.out.println("key: " + new String(entry.getKey()) + " | value: " + new String(entry.getValue()));
        }
    }

    @Test
    public void testRedisObj() {
        Map<String, Object> properties = new HashMap<>();
        properties.put("123", "hello");
        properties.put("abc", 456);

        redisTemplate.opsForHash().putAll("hash", properties);

        Map<Object, Object> ans = redisTemplate.opsForHash().entries("hash");


        System.out.println("ans: " + ans);
    }

    @Test
    public void testRedisList() {
        redisTemplate.opsForList().leftPushAll("list", "123", "234", "adsf");

        String first = redisTemplate.opsForList().leftPop("list");
        System.out.println(first);
    }


    @Test
    public void testGuava() {
        LoadingCache<String, String> cache = CacheBuilder.newBuilder().build(new CacheLoader<String, String>() {
            @Override
            public String load(String key) throws Exception {
                if ("hello".equals(key)) {
                    return "word";
                }
                return null;
            }
        });

        String word = cache.getUnchecked("hello");
        System.out.println(word);

        try {
            System.out.println(cache.getUnchecked("word"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
