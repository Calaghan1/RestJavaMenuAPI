package ServletsTests;

import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.menu.service.MenuService;
import org.menu.service.RestaurantService;
import org.menu.servlet.MenuServlet;
import org.menu.servlet.RestaurantsServlet;
import org.menu.servlet.dto.MenuDto;
import org.menu.servlet.dto.RestaurantsDto;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

public class RestaurantServlet {
    private final HttpServletRequest request = mock(HttpServletRequest.class);
    private final HttpServletResponse response = mock(HttpServletResponse.class);
    private final RestaurantService menuService = mock(RestaurantService.class);
    private final RestaurantsServlet servlet = new RestaurantsServlet(menuService);

    @Test
    void getTest() throws IOException, ServletException {
        RestaurantsDto restaurantsDto = new RestaurantsDto();
        restaurantsDto.setId(1);
        restaurantsDto.setName("Test");
        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);
        when(request.getMethod()).thenReturn("GET");
        when(request.getServletPath()).thenReturn("/menu");
        when(request.getParameter("id")).thenReturn("1");
        when(menuService.getById(1)).thenReturn(restaurantsDto);
        servlet.service(request, response);
        verify(response).setContentType("application/json");
        verify(response).setCharacterEncoding("UTF-8");
        Gson gson = new Gson();
        String json = gson.toJson(restaurantsDto);
        Assertions.assertEquals(json.toString(), stringWriter.toString());
    }
    @Test
    void getTest2() throws IOException, ServletException {
        List<RestaurantsDto> restaurantsDtoList = new ArrayList<>();
        RestaurantsDto restaurantsDto = new RestaurantsDto(1, "Test",  null);
        restaurantsDtoList.add(restaurantsDto);
        RestaurantsDto restaurantsDto1 = new RestaurantsDto(2, "Test", null);
        restaurantsDtoList.add(restaurantsDto1);
        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);
        when(request.getMethod()).thenReturn("GET");
        when(request.getServletPath()).thenReturn("/menuall");
        when(menuService.getAll()).thenReturn(restaurantsDtoList);
        servlet.service(request, response);
        verify(response).setContentType("application/json");
        Gson gson = new Gson();
        String json = gson.toJson(restaurantsDtoList);
        Assertions.assertEquals(json.toString(), stringWriter.toString());
    }
}
