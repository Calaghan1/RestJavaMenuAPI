package ServletsTests;

import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.menu.service.RestaurantService;
import org.menu.servlet.RestaurantsServlet;
import org.menu.servlet.dto.RestaurantsDto;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

class RestaurantServletTest {
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
    void getTestFailed() throws IOException, ServletException {
        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);
        when(request.getMethod()).thenReturn("GET");
        when(request.getServletPath()).thenReturn("/restaurant");
        when(request.getParameter("id")).thenReturn("1");
        when(restaurantService.getById(1)).thenReturn(null);
        servlet.service(request, response);
        verify(response).setContentType("application/json");
        verify(response).setCharacterEncoding("UTF-8");
        Assertions.assertEquals("Not found", stringWriter.toString().trim());
    }

    @Test
    void getTest2() throws IOException, ServletException {
        List<RestaurantsDto> restaurantsDtoList = new ArrayList<>();
        RestaurantsDto restaurantsDto = new RestaurantsDto(1, "Test", null);
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
        String jsonInput = "{\"name\":\"Test\",\"description\":\"Test\"}";
        RestaurantsDto savedRestaurantsDto = new RestaurantsDto(1, "Test", null); // Object returned with ID

        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        BufferedReader reader = new BufferedReader(new StringReader(jsonInput));
        when(request.getReader()).thenReturn(reader);
        when(response.getWriter()).thenReturn(writer);
        when(request.getMethod()).thenReturn("POST");
        when(restaurantService.save(any(RestaurantsDto.class))).thenReturn(savedRestaurantsDto);  // Return saved menu

        servlet.service(request, response);

        verify(response).setContentType("application/json");
        verify(response).setCharacterEncoding("UTF-8");

        verify(restaurantService).save(any(RestaurantsDto.class));

        Assertions.assertEquals("{\"message\": \"Menu created successfully\"}", stringWriter.toString().trim());
    }

    @Test
    void postTestFailed() throws IOException, ServletException {
        String jsonInput = "{\"name\":\"Test\",\"description\":\"Test\"}";

        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        BufferedReader reader = new BufferedReader(new StringReader(jsonInput));

        when(request.getReader()).thenReturn(reader);
        when(response.getWriter()).thenReturn(writer);
        when(request.getMethod()).thenReturn("POST");
        when(restaurantService.save(any(RestaurantsDto.class))).thenReturn(null);  // Return saved menu


        servlet.service(request, response);

        verify(response).setContentType("application/json");
        verify(response).setCharacterEncoding("UTF-8");


        Assertions.assertEquals("Internal server error", stringWriter.toString().trim());
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

    @Test
    void putFailedTest() throws IOException, ServletException {
        String jsonInput = "{\"name\":\"Test\"}";
        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        BufferedReader reader = new BufferedReader(new StringReader(jsonInput));
        when(response.getWriter()).thenReturn(writer);
        when(request.getReader()).thenReturn(reader);
        when(request.getMethod()).thenReturn("PUT");
        when(request.getParameter("id")).thenReturn("1");
        when(restaurantService.update(any(RestaurantsDto.class), anyInt())).thenReturn(null);
        servlet.service(request, response);

        Assertions.assertEquals("Internal server error", stringWriter.toString().trim());
    }

    @Test
    void deleteTest() throws IOException, ServletException {
        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);
        when(request.getMethod()).thenReturn("DELETE");
        when(request.getParameter("id")).thenReturn("1");
        when(restaurantService.delete(1)).thenReturn(true);
        servlet.service(request, response);
        Assertions.assertEquals("{\"message\": \"Restaurant deleted successfully\"}", stringWriter.toString().trim());
    }

    @Test
    void deleteFailTest() throws IOException, ServletException {
        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);
        when(request.getMethod()).thenReturn("DELETE");
        when(request.getParameter("id")).thenReturn("1");
        when(restaurantService.delete(1)).thenReturn(false);
        servlet.service(request, response);
        Assertions.assertEquals("Internal server error", stringWriter.toString().trim());
    }
}
