package com.sl0v3c.samples.models;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Reference;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import java.io.Serializable;
import java.util.List;

@Data
public class Goods implements Serializable {
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
