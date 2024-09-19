package ServletsTests;

import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.menu.service.RestaurantService;
import org.menu.servlet.RestaurantsServlet;
import org.menu.servlet.dto.MenuDto;
import org.menu.servlet.dto.RestaurantsDto;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

public class RestaurantServletTest {
    private final HttpServletRequest request = mock(HttpServletRequest.class);
    private final HttpServletResponse response = mock(HttpServletResponse.class);
    private final RestaurantService restaurantService = mock(RestaurantService.class);
    private final RestaurantsServlet servlet = new RestaurantsServlet(restaurantService);

    @Test
    void getTest() throws IOException, ServletException {
        RestaurantsDto restaurantsDto = new RestaurantsDto();
        restaurantsDto.setId(1);
        restaurantsDto.setName("Test");
        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);
        when(request.getMethod()).thenReturn("GET");
        when(request.getServletPath()).thenReturn("/restaurant");
        when(request.getParameter("id")).thenReturn("1");
        when(restaurantService.getById(1)).thenReturn(restaurantsDto);
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
        when(request.getServletPath()).thenReturn("/restAll");
        when(restaurantService.getAll()).thenReturn(restaurantsDtoList);
        servlet.service(request, response);
        verify(response).setContentType("application/json");
        Gson gson = new Gson();
        String json = gson.toJson(restaurantsDtoList);
        Assertions.assertEquals(json.toString(), stringWriter.toString());
    }
    @Test
    void postTest() throws IOException, ServletException {
        // Initialize inputs and expected results
        String jsonInput = "{\"name\":\"Test\",\"description\":\"Test\"}";
        RestaurantsDto restaurantsDto = new RestaurantsDto(0, "Test", null); // No ID initially
        RestaurantsDto savedRestaurantsDto = new RestaurantsDto(1, "Test", null); // Object returned with ID

        // Set up mock objects and interactions
        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        BufferedReader reader = new BufferedReader(new StringReader(jsonInput));

        when(request.getReader()).thenReturn(reader);
        when(response.getWriter()).thenReturn(writer);
        when(request.getMethod()).thenReturn("POST");
        when(restaurantService.save(any(RestaurantsDto.class))).thenReturn(savedRestaurantsDto);  // Return saved menu

        // Execute the servlet POST method
        servlet.service(request, response);

        // Verify the content type and character encoding are as expected
        verify(response).setContentType("application/json");
        verify(response).setCharacterEncoding("UTF-8");

        // Verify savedMenuDto is returned instead of null
        verify(restaurantService).save(any(RestaurantsDto.class));

        // Check the response message
        Assertions.assertEquals("{\"message\": \"Menu created successfully\"}", stringWriter.toString().trim());
    }
    @Test
    void putTest() throws IOException, ServletException {
        String jsonInput = "{\"name\":\"Test\"}";
        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        BufferedReader reader = new BufferedReader(new StringReader(jsonInput));
        RestaurantsDto savedRestaurantsDto = new RestaurantsDto(1, "Test", null);

        when(response.getWriter()).thenReturn(writer);
        when(request.getReader()).thenReturn(reader);
        when(request.getMethod()).thenReturn("PUT");
        when(request.getParameter("id")).thenReturn("1");
        when(restaurantService.update(any(RestaurantsDto.class), anyInt())).thenReturn(savedRestaurantsDto);
        servlet.service(request, response);

        Assertions.assertEquals("{\"message\": \"Restaurant updated successfully\"}", stringWriter.toString().trim());
    }
}
