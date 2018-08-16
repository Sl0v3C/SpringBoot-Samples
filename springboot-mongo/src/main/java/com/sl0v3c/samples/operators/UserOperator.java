package com.sl0v3c.samples.operators;

import com.sl0v3c.samples.entity.User;
import com.sl0v3c.samples.repositories.account.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;
import java.util.HashMap;
import java.util.List;

import static org.springframework.data.mongodb.core.query.Criteria.where;

@Component
public class UserOperator {
    private UserRepository userRepository;
    private MongoTemplate mongoTemplate;

    @Autowired
    public UserOperator(UserRepository userRepository, MongoTemplate userMongoTemplate) {
        this.userRepository = userRepository;
        this.mongoTemplate = userMongoTemplate;
    }

    public User findById(String id) {
        User user = userRepository.findUserById(id);

        return user;
    }

    public User findByName(String name) {
        User user = userRepository.findUserByName(name);

        return user;
    }

    public User findByNameAndPhone(String name, String phone) {
        User user = userRepository.findUserByNameAndPhone(name, phone);

        return user;
    }

    public User save(User user) {
        return userRepository.save(user);
    }

    public List<User> getAllUsers(String name, String nickname) {
        List<User> userList = mongoTemplate.find(
                new Query(where("name").is(name).and("nickname").is(nickname)),
                User.class);

        return userList;
    }
}