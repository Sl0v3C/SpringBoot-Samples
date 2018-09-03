package com.sl0v3c.samples.config.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import java.util.List;

@Configuration
public class RedisClusterConfig {
    private List<String> nodes;

    public RedisClusterConfig(@Autowired ClusterProperties clusterProperties) {
        this.nodes = clusterProperties.getNodes();
    }

    @Bean("clusterConnection")
    public RedisConnectionFactory connectionFactory() {
        return new JedisConnectionFactory(
                new RedisClusterConfiguration(this.nodes));
    }

    @Bean
    public RedisTemplate<?, ?> redisClusterTemplate(RedisConnectionFactory clusterConnection) {
        RedisTemplate<?, ?> clusterTemplate = new RedisTemplate<>();
        clusterTemplate.setConnectionFactory(clusterConnection);
        clusterTemplate.setHashKeySerializer(new StringRedisSerializer());
        clusterTemplate.setKeySerializer(new StringRedisSerializer());

        return clusterTemplate;
    }
}
