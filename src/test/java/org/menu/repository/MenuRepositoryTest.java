package org.menu.repository;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.menu.db.ConnectionManager;
import org.menu.servlet.dto.MenuDto;
import org.testcontainers.containers.PostgreSQLContainer;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

class RepoTest {
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
        ConnectionManager connectionManager = new ConnectionManager(postgres.getJdbcUrl(), postgres.getUsername(), postgres.getPassword());
        dishesRepository = new DishesRepository(connectionManager);
        menuRepository = new MenuRepository(connectionManager);
    }

    @Test
    void findById() {
        menuRepository.initTable();
        MenuDto menuDto = new MenuDto();
        menuDto.setName("Test");
        menuDto.setDescription("HAhAHAAHA");
        menuRepository.save(menuDto);
        MenuDto menuDto2 = menuRepository.findById(1);
        assertNotEquals(null, menuDto2);
        assertEquals(menuDto.getName(), menuDto2.getName());
        assertEquals(menuDto.getDescription(), menuDto2.getDescription());
    }

    @Test
    void findAll() {
        menuRepository.initTable();
        MenuDto menuDto = new MenuDto();
        menuDto.setName("Test");
        menuDto.setDescription("HAhAHAAHA");
        menuRepository.save(menuDto);
        MenuDto menuDto2 = new MenuDto();
        menuDto2.setName("Test2");
        menuDto2.setDescription("HAhAHAAAHA");
        menuRepository.save(menuDto2);
        List<MenuDto> menuDtos = menuRepository.findAll();
        assertNotEquals(null, menuDtos);
        assertEquals(2, menuDtos.size());
        assertEquals(menuDto.getName(), menuDtos.get(0).getName());
        assertEquals(menuDto.getDescription(), menuDtos.get(0).getDescription());
        assertEquals(menuDto2.getName(), menuDtos.get(1).getName());
        assertEquals(menuDto2.getDescription(), menuDtos.get(1).getDescription());
    }

    @Test
    void update() {
    }

    @Test
    void delete() {
    }
}