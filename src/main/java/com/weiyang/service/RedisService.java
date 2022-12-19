package com.weiyang.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.Collections;

@Service
public class RedisService {
    @Autowired
    private JedisPool jedisPool;

    /**
     * 設置值
     */
    public void setValue(String key, Long value) {
        Jedis jedisClient = jedisPool.getResource();
        jedisClient.set(key, value.toString());
        jedisClient.close();
    }

    /**
     * 獲取key的值
     *
     * @param key
     * @return
     */
    public String getValue(String key) {
        Jedis jedisClient = jedisPool.getResource();
        String value = jedisClient.get(key);
        jedisClient.close();
        return value;
    }

    public boolean stockDeductValidator(String key) {
        try (Jedis jedisClient = jedisPool.getResource()) {
            String script = "if redis.call('exists',KEYS[1]) == 1 then\n" +
                    " local stock = tonumber(redis.call('get',KEYS[1]))\n" +
                    " if( stock <=0 ) then\n" +
                    " return -1\n" +
                    " end;\n" +
                    " redis.call('decr',KEYS[1]);\n" +
                    " return stock - 1;\n" +
                    " end;\n" +
                    " return -1;";

            Long stock = (Long) jedisClient.eval(script,
                    Collections.singletonList(key), Collections.emptyList());
            if (stock < 0) {
                System.out.println("庫存不足");
                return false;
            } else {
                System.out.println("恭喜搶購成功");
                return true;
            }

        } catch (Throwable throwable) {
            System.out.println("庫存扣減失敗" + throwable);
            return false;
        }
    }

}
