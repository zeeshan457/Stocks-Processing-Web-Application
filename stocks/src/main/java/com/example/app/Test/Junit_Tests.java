package com.example.app.Test;

import com.example.app.Data.Entity.StocksEntity;
import com.example.app.Data.Entity.UserEntity;
import com.example.app.Data.Repository.StocksRepository;
import com.example.app.Data.Repository.UserRepository;
import com.example.app.Data.Role;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Junit_Tests {

    /**
     * Attributes and injecting
     */
    @Autowired
    private UserRepository repo = mock(UserRepository.class);
    @Autowired
    private StocksRepository repo2 = mock(StocksRepository.class);

    /**
     *
     * This test PASSED, to save a user on the system.
     *
     */
    @Test
    public void testSaveUserEntity() {

        // Actions
        UserEntity testUser = new UserEntity("testing1", "12345", Role.USER);
        repo.save(testUser);
        // Assert
        UserEntity savedUser = repo.findById(testUser.getId()).orElse(null);
        assertNotNull("Saved user should not be null", savedUser);
        assertEquals("testing1", savedUser.getUsername());
        assertEquals("12345", savedUser.getPassword());
        assertEquals(Role.USER, savedUser.getRoles());
    }

    /**
     *
     * This test PASSED, to save users with different roles.
     *
     */
    @Test
    public void testSaveUserEntityWithDifferentRoles() {

        // Actions
        UserEntity user1 = new UserEntity("user1", "pass1", Role.USER);
        UserEntity user2 = new UserEntity("user2", "pass2", Role.ADMIN);
        repo.save(user1);
        repo.save(user2);
        // Assert
        UserEntity savedUser1 = repo.findById(user1.getId()).orElse(null);
        assertNotNull("Saved user1 should not be null", savedUser1);
        assertEquals("user1", savedUser1.getUsername());
        assertEquals("pass1", savedUser1.getPassword());
        assertEquals(Role.USER, savedUser1.getRoles());
        UserEntity savedUser2 = repo.findById(user2.getId()).orElse(null);
        assertNotNull("Saved user2 should not be null", savedUser2);
        assertEquals("user2", savedUser2.getUsername());
        assertEquals("pass2", savedUser2.getPassword());
        assertEquals(Role.ADMIN, savedUser2.getRoles());
    }

    /**
     *
     * This test PASSED, to save users with different passwords.
     *
     */
    @Test
    public void testSaveUserEntityWithDifferentPasswords() throws Exception {

        // Actions
        UserEntity testUser = new UserEntity("testing2", "12345", Role.USER);
        this.repo.save(testUser);

        // Change the user's password
        testUser.setPassword("54321");

        // Save the user entity again
        this.repo.save(testUser);

        // Fetch the user entity from the repository
        UserEntity fetchedUser = this.repo.findByUsername("testing2");

        // Assert
        assertNotNull("User should not be null", fetchedUser);
        assertEquals("testing2", fetchedUser.getUsername());
        assertEquals("54321", fetchedUser.getPassword());
        assertEquals(Role.USER, fetchedUser.getRoles());
    }

    /**
     *
     * This test PASSED, to save stock data.
     *
     */
    @Test
    public void testAddStock() {

        // Action
        StocksEntity stock = new StocksEntity("V", "Visa");
        repo2.save(stock);
        // Assert
        assertNotNull("stock should not be null", stock);
        assertEquals("V", stock.getSymbol());
        assertEquals("Visa", stock.getInformation());
    }
}
