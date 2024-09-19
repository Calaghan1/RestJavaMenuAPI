package ServiceTests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.menu.model.Restaurants;
import org.menu.repository.DishesRepository;
import org.menu.repository.MenuRepository;
import org.menu.repository.RestaurantsRepository;
import org.menu.service.MenuService;
import org.menu.service.RestaurantService;
import org.menu.servlet.dto.RestaurantsDto;
import org.mockito.Mockito;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;

public class RestaurantServiceTest {
    RestaurantsRepository restaurantsRepository = Mockito.mock(RestaurantsRepository.class);
    RestaurantService restaurantService = new RestaurantService(restaurantsRepository);

    @Test
    public void getByIdTest() throws SQLException {
        Restaurants restaurants = new Restaurants();
        restaurants.setId(1);
        restaurants.setName("Restaurant");
        Mockito.when(restaurantsRepository.findById(Mockito.anyInt())).thenReturn(restaurants);
        RestaurantsDto restaurantsDto1 = restaurantService.getById(1);
        Assertions.assertNotNull(restaurantsDto1);
        Assertions.assertEquals(restaurants.getId(), restaurantsDto1.getId());
        Assertions.assertEquals(restaurants.getName(), restaurantsDto1.getName());
    }
    @Test
    public void getAllTest() throws SQLException {
        List<Restaurants> restaurantsList = new ArrayList<>();
        Restaurants restaurants = new Restaurants();
        restaurants.setId(1);
        restaurants.setName("Restaurant");
        restaurantsList.add(restaurants);
        Restaurants restaurants1 = new Restaurants();
        restaurants1.setId(2);
        restaurants1.setName("Restaurant");
        restaurantsList.add(restaurants1);
        Mockito.when(restaurantsRepository.findAll()).thenReturn(restaurantsList);
        List<RestaurantsDto> restaurantsDtoList1 = restaurantService.getAll();
        Assertions.assertNotNull(restaurantsDtoList1);
        Assertions.assertEquals(restaurantsList.size(), restaurantsDtoList1.size());
    }
    @Test
    public void saveTest() throws SQLException {
        Restaurants restaurants = new Restaurants();
        restaurants.setId(1);
        restaurants.setName("Restaurant");
        RestaurantsDto restaurantsDto = new RestaurantsDto();
        restaurantsDto.setId(1);
        restaurantsDto.setName("Restaurant");
        Mockito.when(restaurantsRepository.save(any(Restaurants.class))).thenReturn(restaurants);
        RestaurantsDto restaurantsDto1 = restaurantService.save(restaurantsDto);
        Assertions.assertNotNull(restaurantsDto1);
        Assertions.assertEquals(restaurantsDto.getId(), restaurantsDto1.getId());
        Assertions.assertEquals(restaurantsDto.getName(), restaurantsDto1.getName());
    }
    @Test
    public void updateTest() throws SQLException {
        Restaurants restaurants = new Restaurants();
        restaurants.setId(1);
        restaurants.setName("Restaurant");
        RestaurantsDto restaurantsDto = new RestaurantsDto();
        restaurantsDto.setId(1);
        restaurantsDto.setName("Restaurant");
        Mockito.when(restaurantsRepository.update(any(Restaurants.class), Mockito.anyInt())).thenReturn(restaurants);
        RestaurantsDto restaurantsDto1 = restaurantService.update(restaurantsDto, 1);
        Assertions.assertNotNull(restaurantsDto1);
        Assertions.assertEquals(restaurantsDto.getId(), restaurantsDto1.getId());
        Assertions.assertEquals(restaurantsDto.getName(), restaurantsDto1.getName());
    }
    @Test
    public void deleteTest() throws SQLException  {
        Mockito.when(restaurantsRepository.delete(Mockito.anyInt())).thenReturn(true);
        boolean deleted = restaurantService.delete(1);
        Assertions.assertTrue(deleted);
    }

    @Test
    void deleteTestFail() throws SQLException  {
        Mockito.when(restaurantsRepository.delete(Mockito.anyInt())).thenThrow(SQLException.class);
        boolean ans =  restaurantService.delete(1);
        Assertions.assertFalse(ans);
    }

    @Test
    void getAllByMenuID() throws SQLException {
        Restaurants restaurants = new Restaurants();
        restaurants.setId(1);
        restaurants.setName("Restaurant");
        Restaurants restaurants1 = new Restaurants();
        restaurants1.setId(2);
        restaurants1.setName("Restaurant");
        List<Restaurants> restaurantsList = new ArrayList<>();
        restaurantsList.add(restaurants);
        restaurantsList.add(restaurants1);
        Mockito.when(restaurantsRepository.findRestByMenuID(1)).thenReturn(restaurantsList);
        List<RestaurantsDto>  restaurantsDtoList = restaurantService.getAllRestaurantsByMenuID(1);
        Assertions.assertNotNull(restaurantsDtoList);
        Assertions.assertEquals(restaurantsList.size(), restaurantsDtoList.size());
    }

    @Test
    void getByIdFailed() throws SQLException {
        Mockito.when(restaurantsRepository.findById(Mockito.anyInt())).thenThrow(SQLException.class);
        RestaurantsDto restaurantsDto = restaurantService.getById(1);
        Assertions.assertNull(restaurantsDto);
    }

    @Test
    void getAllFailedTest() throws SQLException {
        Mockito.when(restaurantsRepository.findAll()).thenThrow(SQLException.class);
        List<RestaurantsDto>  restaurantsDtoList = restaurantService.getAll();
        Assertions.assertNull(restaurantsDtoList);
    }
    @Test
    void saveTestFailed() throws SQLException {
        Mockito.when(restaurantsRepository.save(any(Restaurants.class))).thenThrow(SQLException.class);
        RestaurantsDto restaurantsDtoList = restaurantService.save(any(RestaurantsDto.class));
        Assertions.assertNull(restaurantsDtoList);
    }
    @Test
    void updateTestFailed() throws SQLException {
        Mockito.when(restaurantsRepository.save(any(Restaurants.class))).thenThrow(SQLException.class);
        RestaurantsDto restaurantsDtoList = restaurantService.save(any(RestaurantsDto.class));
        Assertions.assertNull(restaurantsDtoList);
    }
    @Test
    void getRestByMenuIDFailed() throws SQLException {
        Mockito.when(restaurantsRepository.findRestByMenuID(Mockito.anyInt())).thenThrow(SQLException.class);
        List<RestaurantsDto> restaurantsDtoList = restaurantService.getAllRestaurantsByMenuID(1);
        Assertions.assertNull(restaurantsDtoList);
     }
}
