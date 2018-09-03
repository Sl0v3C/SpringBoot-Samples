package com.sl0v3c.samples.repositories;

import com.sl0v3c.samples.config.redis.RedisStandaloneConfig;
import com.sl0v3c.samples.models.Goods;
import com.sl0v3c.samples.models.WarehouseInfo;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes= RedisStandaloneConfig.class)
@ContextConfiguration(classes = GoodsRepository.class)
public class GoodsRepositoryTest<K, V> {

    //@Autowired
    private RedisOperations<K, V> operations;

    @Autowired
    private GoodsRepository goodsRepository;


    Goods adidas = new Goods("adidas 3", "BasketBall 23", 466, "Adidas basketball shoes",
            new WarehouseInfo("727", 0.15));
    Goods adidas2 = new Goods("adidas 3", "FootBall 9", 567, "Adidas football shoes",
            new WarehouseInfo("727", 0.13));
    Goods nike = new Goods("nike 9", "FootBall 10", 939, "Nike football shoes",
            new WarehouseInfo("007", 0.12));

    @After
    public void cleanUp() {
        goodsRepository.deleteAll();
    }

    @Test
    public void saveSingleEntity() {
        //redisTemplate.opsForValue().set("adidas 3", adidas);
        goodsRepository.save(adidas);
        //Goods goods = redisTemplate.opsForValue().get("adidas 3");

        /*assertEquals(goods.getDesc(), "Adidas basketball shoes");
        assertEquals(goods.getName(), "adidas 3");
        assertEquals(goods.getPrice(), 466);
        assertEquals(goods.getPublicName(), "BasketBall 23");
        assertEquals(goods.getWarehouseInfo().getCode(), "727");
        assertEquals(goods.getWarehouseInfo().getTaxRate(), 0.15, 0.0);*/
    }

}
