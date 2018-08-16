package com.sl0v3c.samples.controller;


import com.sl0v3c.samples.entity.User;
import com.sl0v3c.samples.operators.UserOperator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController // spring-boot-starter-web
@Slf4j
@Validated
@RequestMapping(value = "/api/test/user") //spring-boot-starter-web
public class UserController {
    private UserOperator userOperator;

    @Autowired
    public UserController(UserOperator userOperator) {
        this.userOperator = userOperator;
    }

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public User getUser(@RequestParam(value="name", required=false) String name,
                        @RequestParam(value="phone", required=false) String phone,
                        @RequestParam(value="id", required=false) String id) {
        User user =  userOperator.findByName(name);
        if (user != null) {
            log.info("find user phone {} nickname {} by name {}", user.phone, user.nickname,
                    name);
        }

        return user;
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public User getUser(@RequestParam(value="name") String name,
                        @RequestParam(value="phone") String phone,
                        @RequestParam(value="nickname") String nickname,
                        @RequestParam(value="english_name") String englishName) {
        User user = new User();
        user.setName(name);
        user.setNickname(nickname);
        user.setPhone(phone);
        user.setEnglishName(englishName);
        user = userOperator.save(user);

        if (user != null) {
            log.info("create user name {} phone {} nickname {} enlish name {}", user.name,
                    user.phone, user.nickname, user.englishName);
        }

        return user;
    }

    @RequestMapping(value="/all", method = RequestMethod.GET)
    @ResponseBody
    public List<User> getUser(@RequestParam(value="name") String name,
                        @RequestParam(value="nickname") String nickname) {
        List<User> users =  userOperator.getAllUsers(name, nickname);

        return users;
    }

}
