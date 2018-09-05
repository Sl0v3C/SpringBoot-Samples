package com.sl0v3c.samples.redis.repositories;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sl0v3c.samples.models.WarehouseInfo;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.WritingConverter;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import java.util.Map;
import static java.util.Collections.singletonMap;

@WritingConverter
public class WarehouseInfoToBytesConverter implements Converter<WarehouseInfo, byte[]> {

    private final Jackson2JsonRedisSerializer<WarehouseInfo> serializer;

    public WarehouseInfoToBytesConverter() {
        serializer = new Jackson2JsonRedisSerializer<WarehouseInfo>(WarehouseInfo.class);
        serializer.setObjectMapper(new ObjectMapper());
    }

    // convert WarehouseInfo.code to Map<String,byte[]>
    /*public Map<String,byte[]> convert(WarehouseInfo source) {
        return singletonMap("code", source.getCode().getBytes());
    }*/

    @Override
    public byte[] convert(WarehouseInfo value) {
        return serializer.serialize(value);
    }
}
