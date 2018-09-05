package com.sl0v3c.samples.messaging;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;

@Slf4j
public class Publisher {
    private RedisTemplate<?, ?> redisTemplate;

    public Publisher(RedisTemplate<?, ?> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void send(String channel, Object message) {
        log.info("Redis send message... {} to channel {}", message, channel);
        redisTemplate.convertAndSend(channel, message);
    }
}
