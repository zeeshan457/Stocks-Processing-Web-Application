package com.example.app.Test;

import com.example.app.Data.Entity.UserEntity;
import com.example.app.Data.Login.UserRepository;
import com.example.app.Data.Role;
import org.aspectj.lang.annotation.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


import static org.junit.Assert.*;
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository repo;

    @Test
    public void TestRegister() throws Exception {
        UserEntity user1 = new UserEntity(1,"Alice", "12345", "USER");
        UserEntity user2 = new UserEntity(1,"Bob", "12345",  "USER");
        //save user, verify has ID value after save
        this.repo.save(user1);
        this.repo.save(user2);
    }
}
