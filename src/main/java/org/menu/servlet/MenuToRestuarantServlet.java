package org.menu.servlet;


import com.google.gson.Gson;
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
    private MenuService service;

    public MenuToRestuarantServlet() {
        service = new MenuService();
    }

    public MenuToRestuarantServlet(MenuService service) {
        this.service = service;
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        try {
            int id = Integer.parseInt(request.getParameter("restId"));
            List<MenuDto> menuDtoList = service.findMenuByRestaurantId(id);
            if (menuDtoList == null) {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            } else {
                Gson gson = new Gson();
                String json = gson.toJson(menuDtoList);
                response.getWriter().write(json);
            }
        } catch (NumberFormatException | IOException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }
}