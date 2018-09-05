package com.sl0v3c.samples.redis.cluster;

import com.sl0v3c.samples.Application;
import com.sl0v3c.samples.config.redis.ClusterProperties;
import com.sl0v3c.samples.config.redis.RedisClusterConfig;
import com.sl0v3c.samples.models.Goods;
import com.sl0v3c.samples.models.WarehouseInfo;
import com.sl0v3c.samples.utils.keyPrefix.KeyPrefix;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
@ContextConfiguration(classes = {RedisClusterConfig.class, ClusterProperties.class} )
@ActiveProfiles(profiles = "dev")
public class RedisClusterTemplateTest {
    @Autowired
    @Qualifier("clusterConnection")
    private RedisConnectionFactory clusterConnection;
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private RedisTemplate<String, Goods> redisClusterTemplate;

    Goods adidas = new Goods("adidas 3", "BasketBall 23", 466, "Adidas basketball shoes",
            new WarehouseInfo("727", 0.15));
    Goods adidas2 = new Goods("adidas 3", "FootBall 9", 567, "Adidas football shoes",
            new WarehouseInfo("727", 0.13));
    Goods nike = new Goods("nike 9", "FootBall 10", 939, "Nike football shoes",
            new WarehouseInfo("007", 0.12));
    final String key1 = new KeyPrefix(adidas.getName(), adidas).getKey();
    final String key2 = new KeyPrefix(adidas2.getName(), adidas2).getKey();
    final String key3 = new KeyPrefix(nike.getName(),  nike).getKey();

    @Before
    public void setUp() {
        redisClusterTemplate.setConnectionFactory(clusterConnection);
        redisClusterTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisClusterTemplate.setKeySerializer(new StringRedisSerializer());
    }

    @After
    public void cleanUp() {
        List<String> list = Arrays.asList(key1, key2, key3);
        redisClusterTemplate.delete(list);
    }

    @Test
    public void saveSingleEntity() {
        // assemble

        // run
        redisClusterTemplate.opsForValue().set(key2, adidas2);

        // verify
        Goods goods = redisClusterTemplate.opsForValue().get(key2);
        assertEquals(goods.getDesc(), "Adidas football shoes");
        assertEquals(goods.getName(), "adidas 3");
        assertEquals(goods.getPrice(), 567);
        assertEquals(goods.getPublicName(), "FootBall 9");
        assertEquals(goods.getWarehouseInfo().getCode(), "727");
        assertEquals(goods.getWarehouseInfo().getTaxRate(), 0.13, 0.0);
    }

    @Test
    public void saveMultiEntities() {
        // assemble
        Map<String, Goods> products = new HashMap<>();
        products.put(key2, adidas2);
        products.put(key3, nike);
        List<String> list = Arrays.asList(key2, key3);

        // run
        redisClusterTemplate.opsForValue().multiSet(products);

        // verify
        List<Goods> goodsList = redisClusterTemplate.opsForValue().multiGet(list);
        assertEquals(goodsList.size(), 2);
    }
}
