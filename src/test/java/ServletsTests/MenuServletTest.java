package ServletsTests;

import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.menu.service.MenuService;
import org.menu.servlet.MenuServlet;
import org.menu.servlet.dto.MenuDto;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

public class MenuServletTest {
    private final HttpServletRequest request = mock(HttpServletRequest.class);
    private final HttpServletResponse response = mock(HttpServletResponse.class);
    private final MenuService menuService = mock(MenuService.class);
    private final MenuServlet servlet = new MenuServlet(menuService);


    @Test
    void getTest() throws IOException, ServletException {
        MenuDto menuDto = new MenuDto();
        menuDto.setId(1);
        menuDto.setName("Test");
        menuDto.setDescription("Test");
        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);
        when(request.getMethod()).thenReturn("GET");
        when(request.getServletPath()).thenReturn("/menu");
        when(request.getParameter("id")).thenReturn("1");
        when(menuService.getById(1)).thenReturn(menuDto);
        servlet.service(request, response);
        verify(response).setContentType("application/json");
        verify(response).setCharacterEncoding("UTF-8");
        Gson gson = new Gson();
        String json = gson.toJson(menuDto);
        Assertions.assertEquals(json.toString(), stringWriter.toString());
    }

    @Test
    void getTest2() throws IOException, ServletException {
        List<MenuDto> menuDtoList = new ArrayList<>();
        MenuDto menuDto1 = new MenuDto(1, "Test", "Test", null);
        menuDtoList.add(menuDto1);
        MenuDto menuDto2 = new MenuDto(2, "Test", "Test", null);
        menuDtoList.add(menuDto2);
        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);
        when(request.getMethod()).thenReturn("GET");
        when(request.getServletPath()).thenReturn("/menuall");
        when(menuService.getAll()).thenReturn(menuDtoList);
        servlet.service(request, response);
        verify(response).setContentType("application/json");
        Gson gson = new Gson();
        String json = gson.toJson(menuDtoList);
        Assertions.assertEquals(json.toString(), stringWriter.toString());
    }
    @Test
    void postTest() throws IOException, ServletException {
        // Initialize inputs and expected results
        String jsonInput = "{\"name\":\"Test\",\"description\":\"Test\"}";
        MenuDto menu = new MenuDto(0, "Test", "Test", null); // No ID initially
        MenuDto savedMenuDto = new MenuDto(1, "Test", "Test", null); // Object returned with ID

        // Set up mock objects and interactions
        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        BufferedReader reader = new BufferedReader(new StringReader(jsonInput));

        when(request.getReader()).thenReturn(reader);
        when(response.getWriter()).thenReturn(writer);
        when(request.getMethod()).thenReturn("POST");
        when(menuService.save(any(MenuDto.class))).thenReturn(savedMenuDto);  // Return saved menu

        // Execute the servlet POST method
        servlet.service(request, response);

        // Verify the content type and character encoding are as expected
        verify(response).setContentType("application/json");
        verify(response).setCharacterEncoding("UTF-8");

        // Verify savedMenuDto is returned instead of null
        verify(menuService).save(any(MenuDto.class));

        // Check the response message
        Assertions.assertEquals("{\"message\": \"Menu created successfully\"}", stringWriter.toString().trim());
    }
        @Test
    void deleteTest() throws IOException, ServletException {
        when(request.getParameter("id")).thenReturn("1");
        when(request.getMethod()).thenReturn("DELETE");
        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);
        when(menuService.delete(1)).thenReturn(true);
        servlet.service(request, response);
        Assertions.assertEquals("{\"message\": \"Menu deleted successfully\"}", stringWriter.toString());
    }
    @Test
    void updateTest() throws IOException, ServletException {
        // Initialize inputs and expected results
        String jsonInput = "{\"name\":\"Test\",\"description\":\"Test\"}";
        MenuDto menu = new MenuDto(0, "Test", "Test", null); // No ID initially
        MenuDto savedMenuDto = new MenuDto(1, "Test", "Test", null); // Object returned with ID

        // Set up mock objects and interactions
        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        BufferedReader reader = new BufferedReader(new StringReader(jsonInput));

        when(request.getReader()).thenReturn(reader);
        when(response.getWriter()).thenReturn(writer);
        when(request.getMethod()).thenReturn("PUT");
        when(request.getParameter("id")).thenReturn("1");
        when(menuService.update(any(MenuDto.class), eq(1))).thenReturn(savedMenuDto);  // Return saved menu

        // Execute the servlet POST method
        servlet.service(request, response);

        // Verify the content type and character encoding are as expected
        verify(response).setContentType("application/json");
        verify(response).setCharacterEncoding("UTF-8");

        // Verify savedMenuDto is returned instead of null
        verify(menuService).update(any(MenuDto.class), eq(1));

        // Check the response message
        Assertions.assertEquals("{\"message\": \"Menu updated successfully\"}", stringWriter.toString().trim());
    }
}
