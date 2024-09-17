package org.menu.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.menu.service.MenuService;
import org.menu.servlet.dto.DishesDto;
import org.menu.servlet.dto.MenuDto;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

public class MenuServiceTest {
    MenuRepository menuRepository = Mockito.mock(MenuRepository.class);
    DishesRepository dishesRepository = Mockito.mock(DishesRepository.class);
    MenuService menuService = new MenuService(menuRepository, dishesRepository);
    @Test
    public void findMenuById() {
        MenuDto menuDto = new MenuDto();
        menuDto.setId(1);
        menuDto.setName("test");
        menuDto.setDescription("description");
        Mockito.when(menuRepository.findById(1)).thenReturn(menuDto);
        MenuDto menuDto1 = menuService.getById(1);
        Assertions.assertNotNull(menuDto1);
        Assertions.assertEquals("test", menuDto1.getName());
        Assertions.assertEquals("description", menuDto1.getDescription());
    }
    @Test
    public void findMenuAndDishesById() {
        MenuDto menuDto = new MenuDto();
        menuDto.setId(1);
        menuDto.setName("test");
        menuDto.setDescription("description");
        DishesDto dishesDto = new DishesDto();
        dishesDto.setId(1);
        dishesDto.setName("testDISH");
        dishesDto.setDescription("descriptionDISH");
        Mockito.when(menuRepository.findById(1)).thenReturn(menuDto);
        List<DishesDto> dishesDtos = new ArrayList<>();
        dishesDtos.add(dishesDto);
        Mockito.when(dishesRepository.findAllByMenuID(1)).thenReturn(dishesDtos);
        MenuDto menuDto1 = menuService.getById(1);
        Assertions.assertNotNull(menuDto1);
        Assertions.assertEquals("test", menuDto1.getName());
        Assertions.assertEquals("description", menuDto1.getDescription());
        Assertions.assertEquals("test", menuDto1.getName());
        Assertions.assertEquals(1, menuDto1.getDishes().size());
        Assertions.assertEquals("testDISH", menuDto1.getDishes().get(0).getName());
        Assertions.assertEquals("descriptionDISH", menuDto1.getDishes().get(0).getDescription());
    }
    @Test
    public void deleteMenuById() {
        Mockito.when(menuRepository.delete(1)).thenReturn(true);
        Assertions.assertTrue(menuService.delete(1));
    }

    @Test
    public void updateMenu() {
        MenuDto menuDto = new MenuDto();
        menuDto.setId(1);
        menuDto.setName("test");
        menuDto.setDescription("description");
        Mockito.when(menuRepository.update(menuDto, 1)).thenReturn(menuDto);
        Mockito.when(menuRepository.findById(1)).thenReturn(menuDto);
        MenuDto menuDto1 = menuService.getById(1);
        Assertions.assertNotNull(menuDto1);
        Assertions.assertEquals("test", menuDto1.getName());
        Assertions.assertEquals("description", menuDto1.getDescription());
        Assertions.assertEquals(1, menuDto1.getId());
    }
}
