package com.sl0v3c.samples.redis.repositories;

import com.sl0v3c.samples.models.Goods;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import java.util.List;

public interface GoodsRepository extends CrudRepository<Goods, String> {

    List<Goods> findByName(String name);

    Page<Goods> findGoodsByName(String name, Pageable page);

    List<Goods> findByNameAndPublicName(String name, String publicName);

    List<Goods> findByNameAndPrice(String firstname, int price);

    List<Goods> findByNameAndWarehouseInfo_Code(String name, String code);

    List<Goods> findByWarehouseInfo_CodeAndWarehouseInfo_TaxRate(String code, double taxRate);

}