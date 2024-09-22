package ServletsTests;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.menu.service.MenuService;
import org.menu.service.RestaurantService;
import org.menu.service.RestaurantToMenuService;
import org.menu.servlet.ConnectMenuAndRestServlet;
import org.menu.servlet.dto.MenuDto;
import org.menu.servlet.dto.RestaurantsDto;
import org.mockito.Mockito;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;


import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ConnectMenuAndRestServletTest {

    private final MenuService menuService = mock(MenuService.class);
    private final RestaurantService restaurantService = mock(RestaurantService.class);
    private final RestaurantToMenuService restaurantToMenuService = mock(RestaurantToMenuService.class);
    private final HttpServletRequest request = mock(HttpServletRequest.class);
    private final HttpServletResponse response = mock(HttpServletResponse.class);
    ConnectMenuAndRestServlet servlet = new ConnectMenuAndRestServlet(menuService, restaurantService, restaurantToMenuService);

    @Test
    void postTest() throws ServletException, IOException {
        MenuDto menuDto = new MenuDto();
        menuDto.setName("Test");
        menuDto.setDescription("Test");
        RestaurantsDto restaurantsDto = new RestaurantsDto();
        restaurantsDto.setName("Test");
        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);
        Mockito.when(request.getMethod()).thenReturn("POST");
        when(request.getParameter("menuId")).thenReturn("1");
        when(request.getParameter("restId")).thenReturn("1");
        when(menuService.getById(1)).thenReturn(menuDto);
        when(restaurantService.getById((1))).thenReturn(restaurantsDto);
        when(restaurantToMenuService.save(1, 1)).thenReturn(true);

        servlet.service(request, response);
        Assertions.assertEquals("Done", stringWriter.toString());
    }
}
