package com.easy.boke.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @Description
 * @Author xiangyanjun
 * @Version V1.0.0
 * @Since 1.0
 * @Date 2021/3/5
 */
@Component
public class RedisLockUtil {

    public static final String LOCK_PREFIX = "redis_lock";
    public static final int LOCK_EXPIRE = 1000; // ms

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     *  Acquire a lock.
     *
     * @param key
     * @return got the lock or not
     */
    public boolean lock(String key){
        String lock = LOCK_PREFIX + key;

        return (Boolean) redisTemplate.execute((RedisCallback) connection -> {

            long expireAt = System.currentTimeMillis() + LOCK_EXPIRE + 1;
            Boolean acquire = connection.setNX(lock.getBytes(), String.valueOf(expireAt).getBytes());
            redisTemplate.expire(lock, LOCK_EXPIRE, TimeUnit.MILLISECONDS);
            if (acquire) {
                return true;
            } else {

                byte[] value = connection.get(lock.getBytes());

                if (Objects.nonNull(value) && value.length > 0) {

                    long expireTime = Long.parseLong(new String(value));

                    if (expireTime < System.currentTimeMillis()) {
                        // 以防锁过期
                        byte[] oldValue = connection.getSet(lock.getBytes(), String.valueOf(System.currentTimeMillis() + LOCK_EXPIRE + 1).getBytes());
                        // 避免死锁
                        return Long.parseLong(new String(oldValue)) < System.currentTimeMillis();
                    }
                }
            }
            return false;
        });
    }

    /**
     * Delete the lock
     *
     * @param key
     */
    public void delete(String key) {
        redisTemplate.delete(key);
    }
}
