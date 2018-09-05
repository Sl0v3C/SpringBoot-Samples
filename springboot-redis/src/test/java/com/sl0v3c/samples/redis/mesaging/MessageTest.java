package com.sl0v3c.samples.redis.mesaging;

import com.sl0v3c.samples.Application;
import com.sl0v3c.samples.config.redis.ClusterProperties;
import com.sl0v3c.samples.config.redis.RedisClusterConfig;
import com.sl0v3c.samples.messaging.MessageListener;
import com.sl0v3c.samples.messaging.Publisher;
import com.sl0v3c.samples.models.Goods;
import com.sl0v3c.samples.models.WarehouseInfo;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
@ContextConfiguration(classes = {RedisClusterConfig.class, ClusterProperties.class, MessageListener.class} )
@ActiveProfiles(profiles = "dev")
public class MessageTest {
    private Publisher publisher;
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private RedisTemplate<String, Goods> redisTemplate;
    Goods nike = new Goods("nike 9", "FootBall 10", 939, "Nike football shoes",
            new WarehouseInfo("007", 0.12));

    @Before
    public void setUp() {
        redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<>(Goods.class));
        publisher = new Publisher(redisTemplate);
    }

    @Test
    public void sendTest() {
        publisher.send("chat", nike);
    }
}
