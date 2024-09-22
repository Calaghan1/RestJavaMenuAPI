package org.menu.repository;

import org.junit.jupiter.api.*;
import org.menu.db.ConnectionManager;
import org.menu.model.Dishes;
import org.menu.model.Menu;
import org.menu.service.ErrorHandler;
import org.testcontainers.containers.PostgreSQLContainer;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Logger;

class DishRepositotyTest {
    Logger logger = Logger.getLogger(this.getClass().getName());
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>(
            "postgres:16-alpine"
    );
    DishesRepository dishesRepository;
    MenuRepository menuRepository;

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
        try {
            ConnectionManager connectionManager = new ConnectionManager(postgres.getJdbcUrl(), postgres.getUsername(), postgres.getPassword());
            dishesRepository = new DishesRepository(connectionManager);
            menuRepository = new MenuRepository(connectionManager);
            menuRepository.initTable();
            dishesRepository.initTable();
        } catch (Exception e) {
            logger.severe(ErrorHandler.errorMassage(this.getClass().getName(), e));
        }

    }

    @AfterEach
    void tearDown() {
        try {
            menuRepository.dropTable();
            dishesRepository.dropTable();
        } catch (Exception e) {
            logger.severe(ErrorHandler.errorMassage(this.getClass().getName(), e));
        }

    }

    @Test
    void FindById() throws SQLException {
        Menu menu = new Menu();
        menu.setName("Test");
        menu.setDescription("Test");
        menuRepository.save(menu);
        Dishes dishes = new Dishes();
        dishes.setName("Test");
        dishes.setDescription("Test");
        dishes.setMenuId(1);
        dishesRepository.save(dishes);
        Dishes dishes1 = dishesRepository.findById(1);
        Assertions.assertNotNull(dishes1);
        Assertions.assertEquals(dishes.getName(), dishes1.getName());
        Assertions.assertEquals(dishes.getDescription(), dishes1.getDescription());
        Assertions.assertEquals(dishes.getMenuId(), dishes1.getMenuId());

    }

    @Test
    void FindAll() throws SQLException {
        Menu menu = new Menu();
        menu.setName("Test");
        menu.setDescription("Test");
        menuRepository.save(menu);
        Dishes dishes = new Dishes();
        dishes.setName("Test");
        dishes.setDescription("Test");
        dishes.setMenuId(1);
        dishesRepository.save(dishes);
        Dishes dishes1 = new Dishes();
        dishes1.setName("Test2");
        dishes1.setDescription("Test2");
        dishes1.setMenuId(1);
        dishesRepository.save(dishes1);
        List<Dishes> dishList = dishesRepository.findAll();
        Assertions.assertNotNull(dishList);
        Assertions.assertEquals(2, dishList.size());
        Assertions.assertEquals(dishList.get(0).getName(), dishes.getName());
        Assertions.assertEquals(dishList.get(1).getName(), dishes1.getName());
    }

    @Test
    void Update() throws SQLException {
        Menu menu = new Menu();
        menu.setName("Test");
        menu.setDescription("Test");
        menuRepository.save(menu);
        Dishes dishes = new Dishes();
        dishes.setName("Test");
        dishes.setDescription("Test");
        dishes.setMenuId(1);
        dishesRepository.save(dishes);
        Dishes dishes1 = new Dishes();
        dishes1.setName("Test2");
        dishes1.setDescription("Test2");
        dishes1.setMenuId(1);
        dishesRepository.update(dishes1, 1);
        Dishes dishes2 = dishesRepository.findById(1);
        Assertions.assertNotNull(dishes2);
        Assertions.assertEquals(dishes1.getName(), dishes2.getName());
        Assertions.assertEquals(dishes1.getDescription(), dishes2.getDescription());
        Assertions.assertEquals(dishes1.getMenuId(), dishes2.getMenuId());
    }

    @Test
    void Delete() throws SQLException {
        Menu menu = new Menu();
        menu.setName("Test");
        menu.setDescription("Test");
        menuRepository.save(menu);
        Dishes dishes = new Dishes();
        dishes.setName("Test");
        dishes.setDescription("Test");
        dishes.setMenuId(1);
        dishesRepository.save(dishes);
        dishesRepository.delete(1);
        Dishes dishes1 = dishesRepository.findById(1);
        Assertions.assertNull(dishes1);
    }
}
