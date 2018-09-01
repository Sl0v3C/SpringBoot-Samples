package com.sl0v3c.samples.redis;

import com.sl0v3c.samples.config.redis.RedisStandaloneConfig;
import com.sl0v3c.samples.models.Goods;
import com.sl0v3c.samples.models.WarehouseInfo;
import com.sl0v3c.samples.utils.keyPrefix.KeyPrefix;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.*;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=RedisStandaloneConfig.class)
public class RedisStandaloneTemplateTest {

    @Autowired
    private RedisTemplate<String, Goods> redisStandaloneTemplate;

    Goods adidas = new Goods("adidas 3", "BasketBall 23", 466, "Adidas basketball shoes",
            new WarehouseInfo("727", 0.15));
    Goods adidas2 = new Goods("adidas 3", "FootBall 9", 567, "Adidas football shoes",
            new WarehouseInfo("727", 0.13));
    Goods nike = new Goods("nike 9", "FootBall 10", 939, "Nike football shoes",
            new WarehouseInfo("007", 0.12));
    final String key1 = new KeyPrefix(adidas.getName(), adidas).getKey();
    final String key2 = new KeyPrefix(adidas2.getName(), adidas2).getKey();
    final String key3 = new KeyPrefix(nike.getName(),  nike).getKey();
    List<String> list = Arrays.asList(key1, key2, key3);

    @After
    public void cleanUp() {
        redisStandaloneTemplate.delete(list);
    }

    @Test
    public void saveSingleEntity() {
        redisStandaloneTemplate.opsForValue().set(key1, adidas);
        Goods goods = redisStandaloneTemplate.opsForValue().get(key1);

        assertEquals(goods.getDesc(), "Adidas basketball shoes");
        assertEquals(goods.getName(), "adidas 3");
        assertEquals(goods.getPrice(), 466);
        assertEquals(goods.getPublicName(), "BasketBall 23");
        assertEquals(goods.getWarehouseInfo().getCode(), "727");
        assertEquals(goods.getWarehouseInfo().getTaxRate(), 0.15, 0.0);
    }

    @Test
    public void saveMultiEntity() {
        Map<String, Goods> products = new HashMap<>();
        products.put(key1, adidas);
        products.put(key2, adidas2);
        products.put(key3, nike);
        redisStandaloneTemplate.opsForValue().multiSet(products);
        List<Goods> goodsList = redisStandaloneTemplate.opsForValue().multiGet(list);

        assertEquals(goodsList.size(), 3);;
    }

}