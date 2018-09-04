package com.sl0v3c.samples.redis;

import com.sl0v3c.samples.Application;
import com.sl0v3c.samples.config.redis.ClusterProperties;
import com.sl0v3c.samples.config.redis.RedisClusterConfig;
import com.sl0v3c.samples.models.Goods;
import com.sl0v3c.samples.models.WarehouseInfo;
import com.sl0v3c.samples.utils.keyPrefix.KeyPrefix;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.connection.RedisClusterNode;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.ClusterOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import java.util.Set;
import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
@ContextConfiguration(classes = {RedisClusterConfig.class, ClusterProperties.class} )
@ActiveProfiles(profiles = "dev")
public class ClusterOperationsTest {
    @Autowired
    @Qualifier("clusterConnection")
    private RedisConnectionFactory clusterConnection;
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private RedisTemplate<String, Goods> redisClusterTemplate;

    Goods nike = new Goods("nike 9", "FootBall 10", 939, "Nike football shoes",
            new WarehouseInfo("007", 0.12));
    final String key = new KeyPrefix(nike.getName(), nike).getKey();

    @Before
    public void setUp() {
        redisClusterTemplate.setConnectionFactory(clusterConnection);
        redisClusterTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisClusterTemplate.setKeySerializer(new StringRedisSerializer());
    }

    @Test(expected = RuntimeException.class)
    public void clusterOperationsTest() {
        // assemble
        redisClusterTemplate.opsForValue().set(key, nike); // it will set to any node
        ClusterOperations<String, Goods> clusterOps = redisClusterTemplate.opsForCluster();
        RedisClusterNode node = new RedisClusterNode("127.0.0.1", 7006); // maybe any other node, need change

        // run
        Set<String> keys = clusterOps.keys(node, "*");
        clusterOps.shutdown(node);

        // verify
        assertEquals(keys.size(), 1);
        clusterOps.ping(node); // it will raise Exception, since the node was shutdown
    }
}
