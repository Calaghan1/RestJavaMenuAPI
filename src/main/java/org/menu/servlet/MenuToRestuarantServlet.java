package org.menu.servlet;


import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.menu.service.MenuService;

import org.menu.servlet.dto.MenuDto;


import java.io.IOException;
import java.util.List;

@WebServlet(name = "MenuToRestuarantSerlet", value = "menu_to_rest")
public class MenuToRestuarantServlet extends HttpServlet {
    private final transient MenuService service = new MenuService();
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        int id = Integer.parseInt(request.getParameter("restId"));
        List<MenuDto> dto = service.findMenuByRestaurantId(id);
        if (dto == null ) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        } else {
            Gson gson = new Gson();
            String json = gson.toJson(dto);
            response.getWriter().write(json);
        }

    }
}