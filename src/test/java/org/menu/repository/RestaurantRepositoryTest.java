package org.menu.repository;

import org.junit.jupiter.api.*;
import org.menu.db.ConnectionManager;
import org.menu.model.Restaurants;
import org.menu.servlet.dto.RestaurantsDto;
import org.testcontainers.containers.PostgreSQLContainer;

import java.sql.SQLException;
import java.util.List;

public class RestaurantRepositoryTest {
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>(
            "postgres:16-alpine"
    );
    RestaurantsRepository restaurantsRepository;
    @BeforeAll
    static void beforeAll() {
        postgres.start();
    }
    @AfterAll
    static void afterAll() {
        postgres.stop();
    }
    @BeforeEach
    void setUp() throws SQLException {
        ConnectionManager connectionManager = new ConnectionManager(postgres.getJdbcUrl(), postgres.getUsername(), postgres.getPassword());
        restaurantsRepository = new RestaurantsRepository(connectionManager);
        restaurantsRepository.initTable();
    }
    @AfterEach
    void tearDown() throws SQLException {
        restaurantsRepository.dropTable();
    }

    @Test
    public void findById() throws SQLException {
        Restaurants restaurants = new Restaurants();
        restaurants.setName("Test");
        restaurantsRepository.save(restaurants);
        Restaurants restaurants1 = restaurantsRepository.findById(1);
        Assertions.assertNotNull(restaurants1);
        Assertions.assertEquals(restaurants.getName(), restaurants1.getName());
    }
    @Test
    public void findAll() throws SQLException {
        Restaurants restaurants = new Restaurants();
        restaurants.setName("Test");
        restaurantsRepository.save(restaurants);
        Restaurants restaurants1 = new Restaurants();
        restaurants1.setName("Test2");
        restaurantsRepository.save(restaurants1);
        List<Restaurants> restaurantsDtos = restaurantsRepository.findAll();
        Assertions.assertNotNull(restaurantsDtos);
        Assertions.assertEquals(2, restaurantsDtos.size());
    }
    @Test
    public void update() throws SQLException {
        Restaurants restaurants = new Restaurants();
        restaurants.setName("Test");
        restaurantsRepository.save(restaurants);
        Restaurants restaurants1 = new Restaurants();
        restaurants1.setName("Test2");
        restaurantsRepository.update(restaurants1, 1);
        Restaurants restaurants2 = restaurantsRepository.findById(1);
        Assertions.assertNotNull(restaurants2);
        Assertions.assertEquals(restaurants1.getName(), restaurants2.getName());
    }
    @Test
    public void delete() throws SQLException {
        Restaurants restaurants = new Restaurants();
        restaurants.setName("Test");
        restaurantsRepository.save(restaurants);
        restaurantsRepository.delete(1);
        Restaurants restaurants1 = restaurantsRepository.findById(1);
        Assertions.assertNull(restaurants1);
    }

}
