package org.example.api.repository;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class CouponCountRepository
{
    private final RedisTemplate<String, String> redisTemplate;
    private static final String REDIS_KEY = "coupon_count";

    public CouponCountRepository(RedisTemplate<String, String> redisTemplate)
    {
        this.redisTemplate = redisTemplate;
    }

    public Long increment()
    {
        return redisTemplate
                .opsForValue()
                .increment(REDIS_KEY);
    }
}
