package com.sl0v3c.samples.redis.repositories;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sl0v3c.samples.models.WarehouseInfo;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;

@ReadingConverter
public class BytesToWarehouseInfoConverter implements Converter<byte[], WarehouseInfo> {

    private final Jackson2JsonRedisSerializer<WarehouseInfo> serializer;

    public BytesToWarehouseInfoConverter() {
        serializer = new Jackson2JsonRedisSerializer<WarehouseInfo>(WarehouseInfo.class);
        serializer.setObjectMapper(new ObjectMapper());
    }

    @Override
    public WarehouseInfo convert(byte[] value) {
        return serializer.deserialize(value);
    }
}