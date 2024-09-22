package org.menu.repository;

import org.junit.jupiter.api.*;

import org.menu.db.ConnectionManager;
import org.menu.model.Menu;
import org.menu.model.Restaurants;
import org.testcontainers.containers.PostgreSQLContainer;

import java.sql.SQLException;
import java.util.List;

class MenuRepositoryTest {
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>(
            "postgres:16-alpine"
    );
    MenuRepository menuRepository;
    RestaurantsRepository restaurantsRepository;
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
    void setUp() throws SQLException {
        ConnectionManager connectionManager = new ConnectionManager(postgres.getJdbcUrl(), postgres.getUsername(), postgres.getPassword());
        menuRepository = new MenuRepository(connectionManager);
        restaurantsRepository = new RestaurantsRepository(connectionManager);
        restaurantMenuRepo = new RestaurantMenuRepo(connectionManager);
        menuRepository.initTable();
    }

    @AfterEach
    void tearDown() throws SQLException {
        menuRepository.dropTable();
    }

    @Test
    void findById() throws SQLException {
        Menu menu = new Menu();
        menu.setName("Test");
        menu.setDescription("HAhAHAAHA");
        menuRepository.save(menu);
        Menu menu1 = menuRepository.findById(1);
        Assertions.assertNotNull(menu1);
        Assertions.assertEquals(menu.getName(), menu1.getName());
        Assertions.assertEquals(menu.getDescription(), menu1.getDescription());
        menuRepository.delete(1);
    }

    @Test
    void findAll() throws SQLException {
        Menu menu = new Menu();
        menu.setName("Test");
        menu.setDescription("HAhAHAAHA");
        menuRepository.save(menu);
        Menu menu1 = new Menu();
        menu1.setName("Test2");
        menu1.setDescription("HAhAHAAAHA");
        menuRepository.save(menu1);
        List<Menu> menuList = menuRepository.findAll();
        Assertions.assertNotNull(menuList);
        Assertions.assertEquals(2, menuList.size());
        Assertions.assertEquals(menu.getName(), menuList.get(0).getName());
        Assertions.assertEquals(menu.getDescription(), menuList.get(0).getDescription());
        Assertions.assertEquals(menu1.getName(), menuList.get(1).getName());
        Assertions.assertEquals(menu1.getDescription(), menuList.get(1).getDescription());
    }

    @Test
    void update() throws SQLException {
        Menu menu = new Menu();
        menu.setName("Test");
        menu.setDescription("HAhAHAAHA");
        menuRepository.save(menu);
        Menu menu1 = new Menu();
        menu1.setName("Test2");
        menu1.setDescription("HAhAHAAAHA");
        menuRepository.update(menu1, 1);
        Menu menu2 = menuRepository.findById(1);
        Assertions.assertNotNull(menu2);
        Assertions.assertEquals(menu1.getName(), menu2.getName());
        Assertions.assertEquals(menu1.getDescription(), menu2.getDescription());
    }

    @Test
    void delete() throws SQLException {
        Menu menu = new Menu();
        menu.setName("Test");
        menu.setDescription("HAhAHAAHA");
        menuRepository.save(menu);
        Menu menu1 = menuRepository.findById(1);
        Assertions.assertNotNull(menu1);
        menuRepository.delete(1);
        Menu menu2 = menuRepository.findById(1);
        Assertions.assertNull(menu2);
    }

    @Test
    void findMenuByRestId() throws SQLException {
        Restaurants restaurants = new Restaurants();
        restaurants.setName("Restaurants");
        restaurantsRepository.initTable();
        restaurantsRepository.save(restaurants);
        Menu menu = new Menu();
        menu.setName("Test");
        menu.setDescription("HAhAHAAHA");
        Menu menu1 = new Menu();
        menu1.setName("Test2");
        menu1.setDescription("HAhAHAAAHA");
        menuRepository.save(menu);
        menuRepository.save(menu1);

        restaurantMenuRepo.init();
        restaurantMenuRepo.save(1, 1);
        restaurantMenuRepo.save(1, 2);

        List<Menu> menuList = menuRepository.findMenuByRestaurantId(1);
        Assertions.assertNotNull(menuList);
        Assertions.assertEquals(2, menuList.size());

    }
}