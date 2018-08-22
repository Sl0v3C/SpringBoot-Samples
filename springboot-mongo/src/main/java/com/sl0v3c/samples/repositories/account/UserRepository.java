package com.sl0v3c.samples.repositories.account;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.sl0v3c.samples.entity.User;

public interface UserRepository extends MongoRepository<User, String> {
    User findUserById(String id);
    User findUserByName(String name);
    User findUserByNameAndPhone(String name, String phone);
    User findUserByUserInfo_AgeAndUserInfo_Email(int age, String email);
}
