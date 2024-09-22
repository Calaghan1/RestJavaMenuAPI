package ServletsTests;

import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.menu.service.MenuService;
import org.menu.servlet.MenuToRestuarantServlet;
import org.menu.servlet.dto.MenuDto;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class MenuToRestaurantServletTest {

    private final HttpServletRequest request = mock(HttpServletRequest.class);
    private final HttpServletResponse response = mock(HttpServletResponse.class);
    private final MenuService menuService = mock(MenuService.class);
    private final MenuToRestuarantServlet servlet = new MenuToRestuarantServlet(menuService);


    @Test
    void getTest() throws ServletException, IOException {
        MenuDto menuDto = new MenuDto();
        menuDto.setId(1);
        menuDto.setName("test");
        menuDto.setDescription("description");
        MenuDto menuDto2 = new MenuDto();
        menuDto2.setId(2);
        menuDto2.setName("test2");
        menuDto2.setDescription("description2");

        List<MenuDto> menuDtos = new ArrayList<>();
        menuDtos.add(menuDto);
        menuDtos.add(menuDto2);

        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);

        when(request.getMethod()).thenReturn("GET");
        when(request.getParameter("restId")).thenReturn("1");
        when(menuService.findMenuByRestaurantId(1)).thenReturn(menuDtos);
        servlet.service(request, response);

        Gson gson = new Gson();
        String json = gson.toJson(menuDtos);

        Assertions.assertEquals(json, stringWriter.toString());
    }
}
