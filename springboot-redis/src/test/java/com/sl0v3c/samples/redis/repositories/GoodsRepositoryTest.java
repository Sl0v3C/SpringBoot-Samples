package com.sl0v3c.samples.redis.repositories;

import com.sl0v3c.samples.Application;
import com.sl0v3c.samples.config.redis.RedisStandaloneConfig;
import com.sl0v3c.samples.models.Goods;
import com.sl0v3c.samples.models.WarehouseInfo;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes= Application.class)
@ContextConfiguration(classes = {GoodsRepository.class, RedisStandaloneConfig.class})
public class GoodsRepositoryTest {
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private RedisTemplate<byte[], byte[]> redisTemplate;
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private GoodsRepository goodsRepository;
    WarehouseInfoToBytesConverter  converter = new WarehouseInfoToBytesConverter();


    Goods adidas = new Goods("adidas 3", "BasketBall 23", 466, "Adidas basketball shoes",
            new WarehouseInfo("727", 0.15));
    Goods nike = new Goods("nike 9", "FootBall 10", 939, "Nike football shoes",
            new WarehouseInfo("007", 0.12));

    @After
    public void cleanUp() {
        goodsRepository.deleteAll();
    }

    @Test
    public void goodsRepositoryTest() {
        // aseemble
        goodsRepository.save(adidas);
        goodsRepository.save(nike);

        // run
        List<Goods> goodsList = goodsRepository.findByName(adidas.getName());
        Optional<Goods> ret = goodsRepository.findById(adidas.getId());

        converter.convert(ret.get().getWarehouseInfo()); // it will convert to {"code":"727","taxRate":0.15}
        long count = goodsRepository.count();

        goodsRepository.delete(adidas);
        goodsRepository.delete(nike);

        // verify
        assertEquals(goodsList.size(), 1);
        assertEquals(goodsList.get(0).getName(), "adidas 3");
        assertEquals(goodsList.get(0).getPrice(), 466);
        assertEquals(goodsList.get(0).getPublicName(), "BasketBall 23");
        assertEquals(goodsList.get(0).getWarehouseInfo().getCode(), "727");
        assertEquals(goodsList.get(0).getWarehouseInfo().getTaxRate(), 0.15, 0.0);
        assertEquals(ret.get().getName(), "adidas 3");
        assertEquals(ret.get().getPrice(), 466);
        assertEquals(ret.get().getPublicName(), "BasketBall 23");
        assertEquals(ret.get().getWarehouseInfo().getCode(), "727");
        assertEquals(ret.get().getWarehouseInfo().getTaxRate(), 0.15, 0.0);
        assertEquals(count, 2);
    }

}
