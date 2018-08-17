package com.sl0v3c.samples.config.mongo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.util.List;

@Configuration
@EnableMongoRepositories(basePackages = "com.sl0v3c.samples.repositories.account",
        mongoTemplateRef = AccountConfig.MONGO_TEMPLATE)
public class AccountConfig extends MongoTemplateConfig {
    static final String MONGO_TEMPLATE = "userMongoTemplate";
    private List<String> uriList;

    public AccountConfig(@Autowired PropertyConfig propertyConfig) {
        this.uriList = propertyConfig.getUrls();
    }

    @Bean(name = MONGO_TEMPLATE)
    public MongoTemplate mogoTemplate() throws Exception {
        MongoTemplate mongoTemplate = null;
        for (String uri : uriList) {
            mongoTemplate = this.getMongoTemplate(uri);
            if (mongoTemplate != null) {
                break;
            }
        }
        return mongoTemplate;
    }
}