package com.sl0v3c.samples.operators;

import com.sl0v3c.samples.entity.User;
import com.sl0v3c.samples.repositories.account.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class UserOperatorTest {
    private UserOperator userOperator;
    private MongoTemplate mongoTemplate;
    private UserRepository userRepository;

    @Before
    public void setup() {
        this.userRepository = mock(UserRepository.class);
        this.mongoTemplate = mock(MongoTemplate.class);
        this.userOperator = new UserOperator(userRepository, mongoTemplate);
    }

    @Test
    public void findByName_Null_ExpectedNull() throws Exception {
        // assemble
        when(userRepository.findUserByName(anyString())).thenReturn(null);

        // run
        User user = userOperator.findByName("1");

        // verify
        assertEquals(user, null);
    }

    @Test
    public void findByName_User_Expected() throws Exception {
        // assemble
        User user = new User();
        user.setId("1234");
        user.setName("测试");
        user.setNickname("tst");
        user.setEnglishName("test");
        user.setPhone("13800000000");
        when(userRepository.findUserByName(anyString())).thenReturn(user);

        // run
        User data = userOperator.findByName("1");

        // verify
        assertNotEquals(data, null);
        assertEquals(data.id, "1234");
        assertEquals(data.name, "测试");
        assertEquals(data.nickname, "tst");
        assertEquals(data.englishName, "test");
        assertEquals(data.phone, "13800000000");
    }

    @Test
    public void findById_Null_ExpectedNull() throws Exception {
        // assemble
        when(userRepository.findUserById(anyString())).thenReturn(null);

        // run
        User user = userOperator.findById("1232323");

        // verify
        assertEquals(user, null);
    }

    @Test
    public void findById_User_Expected() throws Exception {
        // assemble
        User user = new User();
        user.setId("12346");
        user.setName("测试");
        user.setNickname("tst");
        user.setEnglishName("test");
        user.setPhone("13800000000");
        when(userRepository.findUserById(anyString())).thenReturn(user);

        // run
        User data = userOperator.findById("12132");

        // verify
        assertNotEquals(data, null);
        assertEquals(data.id, "12346");
        assertEquals(data.name, "测试");
        assertEquals(data.nickname, "tst");
        assertEquals(data.englishName, "test");
        assertEquals(data.phone, "13800000000");
    }

    @Test
    public void findByNameAndPhone_Null_ExpectedNull() throws Exception {
        // assemble
        when(userRepository.findUserByNameAndPhone(anyString(), anyString())).thenReturn(null);

        // run
        User user = userOperator.findByNameAndPhone("test", "121212");

        // verify
        assertEquals(user, null);
    }

    @Test
    public void findByNameAndPhone_User_Expected() throws Exception {
        // assemble
        User user = new User();
        user.setId("123452");
        user.setName("测试");
        user.setNickname("tst");
        user.setEnglishName("test");
        user.setPhone("13800000000");
        when(userRepository.findUserByNameAndPhone(anyString(), anyString())).thenReturn(user);

        // run
        User data = userOperator.findByNameAndPhone("test", "12132");

        // verify
        assertNotEquals(data, null);
        assertEquals(data.id, "123452");
        assertEquals(data.name, "测试");
        assertEquals(data.nickname, "tst");
        assertEquals(data.englishName, "test");
        assertEquals(data.phone, "13800000000");
    }

    @Test
    public void save_Null_ExpectedNull() throws Exception {
        // assemble
        when(userRepository.save(any())).thenReturn(null);

        // run
        User user = userOperator.save(null);

        // verify
        assertEquals(user, null);
    }

    @Test
    public void save_User_Expected() throws Exception {
        // assemble
        User user = new User();
        user.setId("123457");
        user.setName("测试");
        user.setNickname("tst");
        user.setEnglishName("test");
        user.setPhone("13800000000");
        when(userRepository.save(any())).thenReturn(user);

        // run
        User data = userOperator.save(user);

        // verify
        assertNotEquals(data, null);
        assertEquals(data.id, "123457");
        assertEquals(data.name, "测试");
        assertEquals(data.nickname, "tst");
        assertEquals(data.englishName, "test");
        assertEquals(data.phone, "13800000000");
    }

    @Test
    public void getAllUsers_Null_ExpectedNull() throws Exception {
        // assemble
        when(mongoTemplate.find(any(), any())).thenReturn(null);

        // run
        List<User> users = userOperator.getAllUsers("1323", "23232");

        // verify
        assertEquals(users, null);
    }

    @Test
    public void getAllUsers_NameAndNickname_ExpectedUserList() throws Exception {
        // assemble
        List<User> users = new ArrayList<>();
        User user = new User();
        user.setId("123457");
        user.setName("测试");
        user.setNickname("tst");
        user.setEnglishName("test");
        user.setPhone("13800000000");
        users.add(user);
        User user1 = new User();
        user1.setId("23232");
        user1.setName("测试2");
        user1.setNickname("tst2");
        user1.setEnglishName("test2");
        user1.setPhone("13800000001");
        users.add(user1);
        when(mongoTemplate.find(any(), any())).thenReturn(Collections.singletonList(users));

        // run
        List<User> data = userOperator.getAllUsers("1323", "23232");

        // verify
        assertNotEquals(users, null);
        assertEquals(users.size(), 2);
        assertEquals(users.get(0).name, "测试");
        assertEquals(users.get(1).name, "测试2");
    }
}
