package com.example.app.Test;

import com.example.app.Data.Entity.UserEntity;
import com.example.app.Data.Repository.UserRepository;
import com.example.app.Data.Role;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserRepositoryTest {

    /**
     * Attributes and injecting
     */
    @Autowired
    private UserRepository repo;

    /**
     *
     * This test PASSED, to register a user in the system
     *
     * @throws Exception if user fails
     */
    @Test
    public void TestRegister() throws Exception {
        UserEntity user1 = new UserEntity("testing1", "12345", Role.USER);
        UserEntity user2 = new UserEntity("testing2", "12345", Role.USER);
        this.repo.save(user1);
        this.repo.save(user2);
    }
}
