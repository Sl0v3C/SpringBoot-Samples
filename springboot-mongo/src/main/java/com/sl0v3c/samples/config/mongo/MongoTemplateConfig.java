package com.sl0v3c.samples.config.mongo;

import com.mongodb.MongoClientURI;
import org.springframework.boot.autoconfigure.mongo.MongoProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.data.mongodb.core.convert.DefaultDbRefResolver;
import org.springframework.data.mongodb.core.convert.DefaultMongoTypeMapper;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;

@Configuration
public class MongoTemplateConfig {
    protected MongoTemplate getMongoTemplate(String uri) throws Exception {
        MongoProperties properties = new MongoProperties();
        properties.setUri(uri);
        MongoDbFactory dbFactory = new SimpleMongoDbFactory(new MongoClientURI(properties.getUri()));
        MappingMongoConverter converter = new MappingMongoConverter(new DefaultDbRefResolver(dbFactory), new MongoMappingContext());
        converter.setTypeMapper(new DefaultMongoTypeMapper(null));
        MongoTemplate mongoTemplate = new MongoTemplate(dbFactory, converter);
        return mongoTemplate;
    }
}
