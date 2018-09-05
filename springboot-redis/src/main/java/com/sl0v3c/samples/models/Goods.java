package com.sl0v3c.samples.models;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;
import java.io.Serializable;

@Data
@NoArgsConstructor
@RedisHash("goods")   // if you want to use repositories, make sure add this annotation. Or it will cause Errors
public class Goods {
    private @Id String id;
    private @Indexed String name;
    private @Indexed String publicName;
    private int price;
    private String desc;
    private WarehouseInfo warehouseInfo;

    public Goods(String name, String publicName, int price, String desc, WarehouseInfo warehouseInfo) {
        this.name = name;
        this.publicName = publicName;
        this.price = price;
        this.desc = desc;
        this.warehouseInfo = warehouseInfo;
    }
}
