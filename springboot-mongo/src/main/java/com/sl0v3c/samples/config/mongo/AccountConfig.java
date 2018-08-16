package com.sl0v3c.samples.config.mongo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories(basePackages = "com.sl0v3c.samples.repositories.account",
        mongoTemplateRef = AccountConfig.MONGO_TEMPLATE)
public class AccountConfig extends MongoTemplateConfig {
    static final String MONGO_TEMPLATE = "userMongoTemplate";
    @Value("${mongodb.account.uri}")
    private String uri;

    @Bean(name = MONGO_TEMPLATE)
    public MongoTemplate mogoTemplate() throws Exception {
        return this.getMongoTemplate(uri);
    }
}