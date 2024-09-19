package ServiceTests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.menu.model.Dishes;
import org.menu.repository.DishesRepository;
import org.menu.repository.MenuRepository;
import org.menu.service.DishesService;
import org.menu.servlet.dto.DishesDto;
import org.mockito.Mockito;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

public class DushesServiceTest {
    DishesRepository dishesRepository = Mockito.mock(DishesRepository.class);
    DishesService dishesService = new DishesService(dishesRepository);

    @Test
    public void findAll() throws SQLException {
        Dishes dishes = new Dishes();
        dishes.setId(1);
        dishes.setName("Test");
        dishes.setDescription("Test");
        dishes.setMenuId(1);
        Dishes dishes1 = new Dishes();
        dishes1.setId(2);
        dishes1.setName("Test2");
        dishes1.setDescription("Test2");
        dishes1.setMenuId(2);
        List<Dishes> dishesDtoList = new ArrayList<>();
        dishesDtoList.add(dishes);
        dishesDtoList.add(dishes1);
        Mockito.when(dishesRepository.findAll()).thenReturn(dishesDtoList);
        List<DishesDto> dishesDtos = dishesService.getAll();
        Assertions.assertNotNull(dishesDtos);
        Assertions.assertEquals(dishesDtoList.size(), dishesDtos.size());
        Assertions.assertEquals(dishesDtos.get(0).getId(), dishesDtoList.get(0).getId());
        Assertions.assertEquals(dishesDtos.get(0).getName(), dishesDtoList.get(0).getName());
        Assertions.assertEquals(dishesDtos.get(0).getDescription(), dishesDtoList.get(0).getDescription());
        Assertions.assertEquals(dishesDtos.get(0).getMenuId(), dishesDtoList.get(0).getMenuId());
    }
    @Test
    public void findById() throws SQLException  {
        Dishes dishes = new Dishes(1, "Test", "Test", 1);
        Mockito.when(dishesRepository.findById(1)).thenReturn(dishes);
        DishesDto dishesDto2 = dishesService.getById(1);
        Assertions.assertNotNull(dishesDto2);
        Assertions.assertEquals(dishes.getId(), dishesDto2.getId());
        Assertions.assertEquals(dishes.getName(), dishesDto2.getName());
        Assertions.assertEquals(dishes.getDescription(), dishesDto2.getDescription());
        Assertions.assertEquals(dishes.getMenuId(), dishesDto2.getMenuId());
    }
    @Test
    public void save() throws SQLException {
        DishesDto dishesDto = new DishesDto();
        dishesDto.setId(1);
        dishesDto.setName("Test");
        dishesDto.setDescription("Test");
        dishesDto.setMenuId(1);
        Dishes dishes = new Dishes();
        dishes.setId(1);
        dishes.setName("Test");
        dishes.setDescription("Test");
        dishes.setMenuId(1);
        Mockito.when(dishesRepository.save(any(Dishes.class))).thenReturn(dishes);
        DishesDto dishesDto2 = dishesService.save(dishesDto);
        Assertions.assertNotNull(dishesDto2);
        Assertions.assertEquals(dishes.getId(), dishesDto2.getId());
        Assertions.assertEquals(dishes.getName(), dishesDto2.getName());
        Assertions.assertEquals(dishes.getDescription(), dishesDto2.getDescription());
        Assertions.assertEquals(dishes.getMenuId(), dishesDto2.getMenuId());
    }
    @Test
    public void delete() throws SQLException {
        Mockito.when(dishesRepository.delete(1)).thenReturn(true);
        boolean deleted = dishesService.delete(1);
        Assertions.assertTrue(deleted);
    }
    @Test
    public void update() throws SQLException {
        DishesDto dishesDto = new DishesDto();
        dishesDto.setId(1);
        dishesDto.setName("Test");
        dishesDto.setDescription("Test");
        dishesDto.setMenuId(1);
        Dishes dishes = new Dishes();
        dishes.setId(1);
        dishes.setName("Test");
        dishes.setDescription("Test");
        dishes.setMenuId(1);
        Mockito.when(dishesRepository.update(any(Dishes.class), eq(1))).thenReturn(dishes);
        DishesDto dishesDto2 = dishesService.update(dishesDto, 1);
        Assertions.assertNotNull(dishesDto2);
        Assertions.assertEquals(dishesDto.getId(), dishesDto2.getId());
        Assertions.assertEquals(dishesDto.getName(), dishesDto2.getName());
        Assertions.assertEquals(dishesDto.getDescription(), dishesDto2.getDescription());
        Assertions.assertEquals(dishesDto.getMenuId(), dishesDto2.getMenuId());
    }

    @Test
    void getByIdFailed() throws SQLException {
        Mockito.when(dishesRepository.findById(1)).thenThrow(SQLException.class);
        DishesDto dishesDto2 = dishesService.getById(1);
        Assertions.assertNull(dishesDto2);
    }
    @Test
    void getAllFailed() throws SQLException {
        Mockito.when(dishesRepository.findAll()).thenThrow(SQLException.class);
        List<DishesDto> dishesDto2 = dishesService.getAll();
        Assertions.assertNull(dishesDto2);
    }
    @Test
    void saveTestFailed() throws SQLException {
        Mockito.when(dishesRepository.save(any(Dishes.class))).thenThrow(SQLException.class);
        DishesDto dishesDto = dishesService.save(any(DishesDto.class));
        Assertions.assertNull(dishesDto);
    }
    @Test
    void deleteTestFailed() throws SQLException {
        Mockito.when(dishesRepository.delete(1)).thenThrow(SQLException.class);
        boolean deleted = dishesService.delete(1);
        Assertions.assertFalse(deleted);
    }


}
