package ServletsTests;

import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kotlin.jvm.internal.unsafe.MonitorKt;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.menu.service.RestaurantService;
import org.menu.service.RestaurantToMenuService;
import org.menu.servlet.RestorauntToMenuServlet;
import org.menu.servlet.dto.RestaurantsDto;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class RestaurantToMenuServletTest {

    private final HttpServletRequest request = mock(HttpServletRequest.class);
    private final HttpServletResponse response = mock(HttpServletResponse.class);
    private final RestaurantToMenuService restaurantToMenuService = mock(RestaurantToMenuService.class);
    private final RestaurantService restaurantService = mock(RestaurantService.class);
    private final RestorauntToMenuServlet servlet = new RestorauntToMenuServlet(restaurantToMenuService, restaurantService);

    @Test
    void getTest() throws ServletException, IOException {
        RestaurantsDto restaurantsDto = new RestaurantsDto();
        restaurantsDto.setId(1);
        restaurantsDto.setName("Restaurant");
        RestaurantsDto restaurantsDto2 = new RestaurantsDto();
        restaurantsDto2.setId(2);
        restaurantsDto2.setName("Restaurant2");
        List<RestaurantsDto> restaurantsDtoList = new ArrayList<>();
        restaurantsDtoList.add(restaurantsDto);
        restaurantsDtoList.add(restaurantsDto2);

        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);
        Mockito.when(request.getMethod()).thenReturn("GET");
        Mockito.when(request.getParameter("menuID")).thenReturn("1");
        Mockito.when(restaurantService.getAllRestaurantsByMenuID(1)).thenReturn(restaurantsDtoList);

        servlet.service(request, response);

        Gson gson = new Gson();
        String json = gson.toJson(restaurantsDtoList);

        Assertions.assertEquals(json.toString(), stringWriter.toString());

    }
    @Test
    void postTest() throws ServletException, IOException {
        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);

        Mockito.when(response.getWriter()).thenReturn(writer);
        Mockito.when(request.getMethod()).thenReturn("POST");
        Mockito.when(request.getParameter("menuID")).thenReturn("1");
        Mockito.when(request.getParameter("restaurantID")).thenReturn("1");
        Mockito.when(restaurantToMenuService.save(1, 1)).thenReturn(true);

        servlet.service(request, response);

        Assertions.assertEquals("Done", stringWriter.toString());
    }
}
