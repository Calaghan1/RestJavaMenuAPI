package org.menu.repository;

import org.junit.jupiter.api.*;
import org.menu.db.ConnectionManager;
import org.testcontainers.containers.PostgreSQLContainer;

import java.sql.SQLException;

public class RestaurantToMenuRepsitoryTest {
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>(
            "postgres:16-alpine"
    );
    RestaurantMenuRepo restaurantMenuRepo;
    @BeforeAll
    static void beforeAll() {
        postgres.start();
    }
    @AfterAll
    static void afterAll() {
        postgres.stop();
    }
    @BeforeEach
    void setUp() {
        ConnectionManager connectionManager = new ConnectionManager(postgres.getJdbcUrl(), postgres.getUsername(), postgres.getPassword());
        restaurantMenuRepo= new RestaurantMenuRepo(connectionManager);
    }
    @Test
    void failedSaveTest() {
        Assertions.assertThrows(SQLException.class, () -> {
            restaurantMenuRepo.save(1, 1);
        });

    }
    @Test
    void saveTest() {
        try {
            restaurantMenuRepo.init();
            restaurantMenuRepo.save(1, 1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
