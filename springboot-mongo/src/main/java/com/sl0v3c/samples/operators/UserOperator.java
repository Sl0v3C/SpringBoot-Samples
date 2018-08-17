package com.sl0v3c.samples.operators;

import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import com.sl0v3c.samples.entity.User;
import com.sl0v3c.samples.repositories.account.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;
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

    public void insert(User user) {
        mongoTemplate.insert(user);
    }

    public long updateMulti(String name, String email, String nickname) {
        UpdateResult result;
        result = mongoTemplate.updateMulti(
                new Query(where("name").is(name).and("email").is(email)),
                new Update().set("nickname", nickname),
                User.class);

        return result.getModifiedCount();

    }

    public long delete(String name) {
        DeleteResult result;
        result = mongoTemplate.remove(new Query(where("name").is(name)), User.class);

        return result.getDeletedCount();
    }

    public void delete(User user) {
        userRepository.delete(user);
    }

    public void deleteById(String id) {
        userRepository.deleteById(id);
    }

    public List<User> getAllUsers(String name, String nickname) {
        List<User> userList = mongoTemplate.find(
                new Query(where("name").is(name).and("nickname").is(nickname)),
                User.class);

        return userList;
    }
}