package com.github.wushujing.light.repository;

import com.github.wushujing.light.LightApplication;
import com.github.wushujing.light.domain.User;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = LightApplication.class)
class UserRepositoryTest {

    @Resource
    private UserRepository userRepository;

    @Test
    void testBaseQuery() throws Exception {
        User user = new User();
        user.setId(1L);
        user.setUsername("test");
        userRepository.save(user);
        List<User> users = userRepository.findAll();
        assertTrue(users != null && users.size() > 0);

        User u = userRepository.findByUsername("test");
        assertNotNull(u);
    }
}