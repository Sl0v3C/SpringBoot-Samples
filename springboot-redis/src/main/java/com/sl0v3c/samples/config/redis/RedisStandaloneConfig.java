package com.sl0v3c.samples.config.redis;

import com.sl0v3c.samples.models.Goods;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
@EnableRedisRepositories
public class RedisStandaloneConfig {

    @Bean("standaloneConnection")
    @Primary // stringRedisTemplate default need only one RedisConnectionFactory, so make it Primary to avoid Error
    public RedisConnectionFactory connectionFactory() {
        // default localhost and port 6379
        return new JedisConnectionFactory();
    }

    @Bean
    public RedisTemplate<?, ?> redisTemplate(RedisConnectionFactory standaloneConnection) {
        RedisTemplate<byte[], byte[]> template = new RedisTemplate<>();  // use byte[] for converting
        template.setConnectionFactory(standaloneConnection);
        template.setKeySerializer(new StringRedisSerializer());
        template.setHashKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new Jackson2JsonRedisSerializer<>(Goods.class));

        return template;
    }

}