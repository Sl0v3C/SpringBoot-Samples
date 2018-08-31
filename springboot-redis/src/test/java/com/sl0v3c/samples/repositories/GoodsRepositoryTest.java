package com.sl0v3c.samples.repositories;

import com.sl0v3c.samples.config.redis.RedisConfig;
import com.sl0v3c.samples.models.Goods;
import com.sl0v3c.samples.models.WarehouseInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=RedisConfig.class)
public class GoodsRepositoryTest {

    @Autowired
    private RedisTemplate<String, Goods> redisTemplate;
    Goods adidas = new Goods("adidas 3", "BasketBall 23", 466, "Adidas basketball shoes",
            new WarehouseInfo("727", 0.15));
    Goods adidas2 = new Goods("adidas 3", "FootBall 9", 567, "Adidas football shoes",
            new WarehouseInfo("727", 0.13));
    Goods nike = new Goods("nike 9", "FootBall 10", 939, "Nike football shoes",
            new WarehouseInfo("007", 0.12));


    @Test
    public void saveSingleEntity() {
        redisTemplate.opsForValue().set("adidas 3", adidas);
        Goods goods = redisTemplate.opsForValue().get("adidas 3");

        assertEquals(goods.getDesc(), "Adidas basketball shoes");
        assertEquals(goods.getName(), "adidas 3");
        assertEquals(goods.getPrice(), 466);
        assertEquals(goods.getPublicName(), "BasketBall 23");
        assertEquals(goods.getWarehouseInfo().getCode(), "727");
        assertEquals(goods.getWarehouseInfo().getTaxRate(), 0.15, 0.0);
    }

}
