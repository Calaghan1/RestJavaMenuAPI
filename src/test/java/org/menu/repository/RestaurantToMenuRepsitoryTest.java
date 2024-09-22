package org.menu.repository;

import org.junit.jupiter.api.*;
import org.menu.db.ConnectionManager;
import org.menu.service.ErrorHandler;
import org.testcontainers.containers.PostgreSQLContainer;

import java.sql.SQLException;
import java.util.logging.Logger;

class RestaurantToMenuRepsitoryTest {
    Logger logger = Logger.getLogger(this.getClass().getName());
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
        restaurantMenuRepo = new RestaurantMenuRepo(connectionManager);
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
            boolean res = restaurantMenuRepo.save(1, 1);
            Assertions.assertTrue(res);
        } catch (Exception e) {
            logger.severe(ErrorHandler.errorMassage(this.getClass().getName(), e));
        }
    }


}
