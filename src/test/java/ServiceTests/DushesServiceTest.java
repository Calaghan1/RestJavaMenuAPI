package ServiceTests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.menu.repository.DishesRepository;
import org.menu.repository.MenuRepository;
import org.menu.service.MenuService;
import org.menu.servlet.dto.DishesDto;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

public class DushesService {
    MenuRepository menuRepository = Mockito.mock(MenuRepository.class);
    DishesRepository dishesRepository = Mockito.mock(DishesRepository.class);
    MenuService menuService = new MenuService(menuRepository, dishesRepository);

    @Test
    public void findAll() {
        Mockito.when(menuRepository.findAll()).thenReturn(new ArrayList<>());
        List<DishesDto> dishesDtos = dishesRepository.findAll();
        Assertions.assertNotNull(dishesDtos);
    }

}
