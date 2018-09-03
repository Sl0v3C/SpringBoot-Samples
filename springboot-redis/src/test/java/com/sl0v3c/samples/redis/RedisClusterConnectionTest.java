package com.sl0v3c.samples.redis;

import com.google.gson.Gson;
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
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.connection.RedisClusterConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.UnsupportedEncodingException;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
/**
 * SpringBoot 提供一个注解@SpringBootTest，它能够测试你的SpringApplication，因为SpringBoot程序的入口是SpringApplication，
 基本的所有配置都会通过入口类去加载，而注解可以引用入口类的配置。
 */
@SpringBootTest(classes = Application.class)
/**
 * @EnableAutoConfiguration注解的作用在于让 Spring Boot 根据应用所声明的依赖来对 Spring 框架进行自动配置，
 * 这就减少了开发人员的工作量。
 * 只需要在主配置 Java 类上添加@EnableAutoConfiguration注解就可以启用自动配置
 */

/**
 * @ContextConfiguration Spring整合JUnit4测试时，使用注解引入多个配置文件
 * @ContextConfiguration(Locations="../applicationContext.xml")
 * @ContextConfiguration(classes = SimpleConfiguration.class)
 * 多个文件时，可用{}
 */
@ContextConfiguration(classes = {RedisClusterConfig.class, ClusterProperties.class} )
/**
 * 指定profiles，可以改变当前spring 的profile，来达到多环境的测试
 * 如果没有指定的话，就需要在test package里的resources里新增一个application-dev.yaml文件，并加入cluster的配置项
 */
@ActiveProfiles(profiles = "dev")
public class RedisClusterConnectionTest {
    @Autowired
    private RedisConnectionFactory clusterConnection;
    private RedisClusterConnection redisClusterConnection;
    private Gson gson;
    private String key;
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private RedisTemplate<String, Goods> redisClusterTemplate;

    Goods adidas = new Goods("adidas football 9", "FootBall 9", 567, "Adidas football shoes",
            new WarehouseInfo("727", 0.13));

    @Before
    public void setUp() {
        redisClusterConnection = clusterConnection.getClusterConnection();
        gson = new Gson();
        key  = new KeyPrefix(adidas.getName(), adidas).getKey();
    }

    @After
    public void cleanUp() {
        redisClusterConnection.del(key.getBytes());
        redisClusterTemplate.delete("123");
    }

    @Test
    public void test() {
        // assemble

        // run
        // maybe the key-value not in the same cluster
        redisClusterConnection.set(key.getBytes(), gson.toJson(adidas).getBytes());
        redisClusterTemplate.opsForValue().set("123", adidas);

        // verify
        try {
            byte[] bytes = redisClusterConnection.get(key.getBytes());
            String data = new String(bytes,"UTF-8"); // byte[] to String with encode charset type
            Goods goods = gson.fromJson(data, Goods.class); // Gson convert json to Java Bean
            assertEquals(goods.getPublicName(), "FootBall 9");
            assertEquals(goods.getWarehouseInfo().getTaxRate(), 0.13, 0.00);
            assertEquals(goods.getWarehouseInfo().getCode(), "727");
            assertEquals(goods.getPrice(), 567);
            assertEquals(goods.getName(), "adidas football 9");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        Goods goods = redisClusterTemplate.opsForValue().get("123");
        assertEquals(goods.getPublicName(), "FootBall 9");
        assertEquals(goods.getWarehouseInfo().getTaxRate(), 0.13, 0.00);
        assertEquals(goods.getWarehouseInfo().getCode(), "727");
        assertEquals(goods.getPrice(), 567);
        assertEquals(goods.getName(), "adidas football 9");
    }

}
