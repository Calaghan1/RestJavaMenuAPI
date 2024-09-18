package ServiceTests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.menu.model.Dishes;
import org.menu.model.Menu;
import org.menu.repository.DishesRepository;
import org.menu.repository.MenuRepository;
import org.menu.service.MenuService;
import org.menu.servlet.dto.DishesDto;
import org.menu.servlet.dto.MenuDto;
import org.mockito.Mockito;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

public class MenuServiceTest {
    MenuRepository menuRepository = Mockito.mock(MenuRepository.class);
    DishesRepository dishesRepository = Mockito.mock(DishesRepository.class);
    MenuService menuService = new MenuService(menuRepository, dishesRepository);
    @Test
    public void findMenuById() throws SQLException {
        Menu menu = new Menu();
        menu.setId(1);
        menu.setName("test");
        menu.setDescription("description");
        Mockito.when(menuRepository.findById(1)).thenReturn(menu);
        MenuDto menuDto1 = menuService.getById(1);
        Assertions.assertNotNull(menuDto1);
        Assertions.assertEquals("test", menuDto1.getName());
        Assertions.assertEquals("description", menuDto1.getDescription());
    }
    @Test
    public void findMenuAndDishesById() throws SQLException {
        Menu menu = new Menu();
        menu.setId(1);
        menu.setName("test");
        menu.setDescription("description");
        Dishes dishes = new Dishes();
        dishes.setId(1);
        dishes.setName("testDISH");
        dishes.setDescription("descriptionDISH");
        Mockito.when(menuRepository.findById(1)).thenReturn(menu);
        List<Dishes> dishesList = new ArrayList<>();
        dishesList.add(dishes);
        Mockito.when(dishesRepository.findAllByMenuID(1)).thenReturn(dishesList);
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
    public void deleteMenuById() throws SQLException {
        Mockito.when(menuRepository.delete(1)).thenReturn(true);
        Assertions.assertTrue(menuService.delete(1));
    }

    @Test
    public void updateMenu() throws SQLException  {
        Menu menu = new Menu();
        menu.setId(1);
        menu.setName("test");
        menu.setDescription("description");
        MenuDto menuDto = new MenuDto();
        menuDto.setId(1);
        menuDto.setName("test");
        menuDto.setDescription("description");
        Mockito.when(menuRepository.update(any(Menu.class), eq(1))).thenReturn(menu);
        MenuDto menuDto1 = menuService.update(menuDto, 1);
        Assertions.assertNotNull(menuDto1);
        Assertions.assertEquals("test", menuDto1.getName());
        Assertions.assertEquals("description", menuDto1.getDescription());
        Assertions.assertEquals(1, menuDto1.getId());
    }
}
