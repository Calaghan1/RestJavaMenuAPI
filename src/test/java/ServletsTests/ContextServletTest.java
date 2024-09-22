package ServletsTests;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletException;
import org.junit.jupiter.api.Test;
import org.menu.db.ConnectionManager;
import org.menu.repository.DishesRepository;
import org.menu.repository.MenuRepository;
import org.menu.repository.RestaurantMenuRepo;
import org.menu.repository.RestaurantsRepository;

import org.menu.servlet.ContextServlet;
import org.mockito.Mockito;

import java.sql.SQLException;

import static org.mockito.Mockito.mock;


class ContextServletTest {
    private final DishesRepository dishesRepository = mock(DishesRepository.class);
    private final MenuRepository menuRepository = mock(MenuRepository.class);
    private final RestaurantsRepository restaurantsRepository = mock(RestaurantsRepository.class);
    private final RestaurantMenuRepo restaurantMenuRepo = mock(RestaurantMenuRepo.class);
    private final ConnectionManager connectionManager = mock(ConnectionManager.class);
    private final ContextServlet contextServlet = new ContextServlet(dishesRepository, menuRepository, restaurantsRepository,
            restaurantMenuRepo, connectionManager);


    @Test
    void contextInitTest() throws SQLException {
        ServletContextEvent sce = new ServletContextEvent(mock(ServletContext.class));
        Mockito.doNothing().when(connectionManager).init();
        Mockito.doNothing().when(menuRepository).initTable();
        Mockito.doNothing().when(restaurantsRepository).initTable();
        Mockito.doNothing().when(dishesRepository).initTable();
        Mockito.doNothing().when(restaurantMenuRepo).init();
        contextServlet.contextInitialized(sce);
    }
}
