package com.sl0v3c.samples.entity;

import com.sl0v3c.samples.entity.embedded.UserInfo;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
public class User {
    @Id
    public String id;
    @Field("name")
    public String name;
    @Field("Phone")
    public String phone;
    @Field("nickname")
    public String nickname;
    @Field("english_name")
    public String englishName;
    public UserInfo userInfo;
}
