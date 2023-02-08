package com.example.app.Test;

import com.example.app.Data.API.StockAPI;
import com.example.app.Data.Entity.UserEntity;
import com.example.app.Data.Repository.UserRepository;
import com.example.app.Data.Role;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.data.provider.Query;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.List;

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

    StockAPI API = new StockAPI();


    /**
     *
     * This test PASSED, to register a user in the system based on the parameters provided.
     *
     * @throws Exception if user fails
     */
    @Test
    public void testRegistration() throws Exception {
        UserEntity testUser = new UserEntity("testing1", "12345", Role.USER);

        this.repo.save(testUser);

        // Testing username, password and Role
        assertEquals("testing1", testUser.getUsername());
        assertEquals("12345", testUser.getPassword());
        assertEquals(Role.USER, testUser.getRoles());
    }

    /**
     *
     * This test PASSED, to fetch stock data based on the test cases.
     *
     * @throws IOException if fetch has failed
     */
    @Test
    public void testGetStockFromAPI() throws IOException {
        Grid<StockAPI> grid = new Grid<>();
        String option = "V";
        int year = -1;

        API.getStockFromAPI(grid, option, year);

        ListDataProvider<StockAPI> dataProvider = (ListDataProvider<StockAPI>) grid.getDataProvider();

        assertNotNull(dataProvider);
    }
}
