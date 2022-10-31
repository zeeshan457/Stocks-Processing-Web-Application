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

    // Injecting from Repository to testing class
    @Autowired
    private UserRepository repo;

    // TEST HAS PASSED
    @Test
    public void TestRegister() throws Exception {
        UserEntity user1 = new UserEntity("Admin", "12345", Role.ADMIN);
        UserEntity user2 = new UserEntity("User", "12345", Role.USER);
        //save user, verify has ID value after save
        this.repo.save(user1);
        this.repo.save(user2);
    }
}
