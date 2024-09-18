package ServletsTests;

import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.menu.service.DishesService;
import org.menu.service.MenuService;
import org.menu.servlet.DishesServlet;
import org.menu.servlet.MenuServlet;
import org.menu.servlet.dto.DishesDto;
import org.menu.servlet.dto.MenuDto;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

public class DishServletTest {
    private final HttpServletRequest request = mock(HttpServletRequest.class);
    private final HttpServletResponse response = mock(HttpServletResponse.class);
    private final DishesService dishesService = mock(DishesService.class);
    private final DishesServlet servlet = new DishesServlet(dishesService);

    @Test
    void getTest() throws IOException, ServletException {
        DishesDto dishDto = new DishesDto();
        dishDto.setId(1);
        dishDto.setName("Test");
        dishDto.setDescription("Test");
        dishDto.setMenuId(1);
        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);
        when(request.getMethod()).thenReturn("GET");
        when(request.getServletPath()).thenReturn("/dish");
        when(request.getParameter("id")).thenReturn("1");
        when(dishesService.getById(1)).thenReturn(dishDto);
        servlet.service(request, response);
        verify(response).setContentType("application/json");
        verify(response).setCharacterEncoding("UTF-8");
        Gson gson = new Gson();
        String json = gson.toJson(dishDto);
        Assertions.assertEquals(json.toString(), stringWriter.toString());
    }
    @Test
    void doTest2() throws IOException, ServletException {
        List<DishesDto> dishesDtos = new ArrayList<>();
        DishesDto dishesDto = new DishesDto(1, "Test", "Test", 1);
        dishesDtos.add(dishesDto);
        DishesDto dishesDto2 = new DishesDto(2, "Test", "Test", 1);
        dishesDtos.add(dishesDto2);
        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);
        when(request.getMethod()).thenReturn("GET");
        when(request.getServletPath()).thenReturn("/dishes");
        when(dishesService.getAll()).thenReturn(dishesDtos);
        servlet.service(request, response);
        verify(response).setContentType("application/json");
        Gson gson = new Gson();
        String json = gson.toJson(dishesDtos);
        Assertions.assertEquals(json.toString(), stringWriter.toString());
    }
    @Test
    void postTest() throws IOException, ServletException {
        // Initialize inputs and expected results
        String jsonInput = "{\"name\":\"Test\",\"description\":\"Test\"}";
        DishesDto dishesDto = new DishesDto(1, "Test", "Test", 1); // No ID initially
        DishesDto savedDto = new DishesDto(1, "Test", "Test", 1); // Object returned with ID

        // Set up mock objects and interactions
        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        BufferedReader reader = new BufferedReader(new StringReader(jsonInput));

        when(request.getReader()).thenReturn(reader);
        when(response.getWriter()).thenReturn(writer);
        when(request.getMethod()).thenReturn("POST");
        when(dishesService.save(any(DishesDto.class))).thenReturn(savedDto);  // Return saved menu

        // Execute the servlet POST method
        servlet.service(request, response);

        // Verify the content type and character encoding are as expected
        verify(response).setContentType("application/json");
        verify(response).setCharacterEncoding("UTF-8");

        // Verify savedMenuDto is returned instead of null
        verify(dishesService).save(any(DishesDto.class));

        // Check the response message
        Assertions.assertEquals("{\"message\": \"Dish created successfully\"}", stringWriter.toString().trim());
    }
    @Test
    void deleteTest() throws IOException, ServletException {
        when(request.getParameter("id")).thenReturn("1");
        when(request.getMethod()).thenReturn("DELETE");
        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);
        when(dishesService.delete(1)).thenReturn(true);
        servlet.service(request, response);
        Assertions.assertEquals("{\"message\": \"Dish deleted successfully\"}", stringWriter.toString());
    }
    @Test
    void updateTest() throws IOException, ServletException {
        // Initialize inputs and expected results
        String jsonInput = "{\"name\":\"Test\",\"description\":\"Test\"}";
        DishesDto menu = new DishesDto(0, "Test", "Test", 1); // No ID initially
        DishesDto savedMenuDto = new DishesDto(1, "Test", "Test", 1); // Object returned with ID

        // Set up mock objects and interactions
        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        BufferedReader reader = new BufferedReader(new StringReader(jsonInput));

        when(request.getReader()).thenReturn(reader);
        when(response.getWriter()).thenReturn(writer);
        when(request.getMethod()).thenReturn("PUT");
        when(request.getParameter("id")).thenReturn("1");
        when(dishesService.update(any(DishesDto.class), eq(1))).thenReturn(savedMenuDto);  // Return saved menu

        // Execute the servlet POST method
        servlet.service(request, response);

        // Verify the content type and character encoding are as expected
        verify(response).setContentType("application/json");
        verify(response).setCharacterEncoding("UTF-8");

        // Verify savedMenuDto is returned instead of null
        verify(dishesService).update(any(DishesDto.class), eq(1));

        // Check the response message
        Assertions.assertEquals("{\"message\": \"Dish updated successfully\"}", stringWriter.toString().trim());
    }
}
