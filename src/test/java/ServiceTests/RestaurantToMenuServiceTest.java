package ServiceTests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.menu.repository.RestaurantMenuRepo;

import org.menu.service.RestaurantToMenuService;
import org.mockito.Mockito;

import java.sql.SQLException;

class RestaurantToMenuServiceTest {
    RestaurantMenuRepo restaurantMenuRepo = Mockito.mock(RestaurantMenuRepo.class);
    RestaurantToMenuService restaurantService = new RestaurantToMenuService(restaurantMenuRepo);

    @Test
    void saveFailedTest() throws SQLException {
        Mockito.when(restaurantMenuRepo.save(1, 1)).thenThrow(SQLException.class);
        boolean result = restaurantService.save(1, 1);
        Assertions.assertFalse(result);
    }

    @Test
    void saveSuccessTest() throws SQLException {
        Mockito.when(restaurantMenuRepo.save(1, 1)).thenReturn(true);
        boolean result = restaurantService.save(1, 1);
        Assertions.assertTrue(result);
    }
}
