package org.menu.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.menu.service.MenuService;
import org.menu.service.RestaurantService;
import org.menu.service.RestaurantToMenuService;
import org.menu.servlet.dto.MenuDto;
import org.menu.servlet.dto.RestaurantsDto;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "ConnectMenuAndRestServlet", value = "/connect")
public class ConnectMenuAndRestServlet extends HttpServlet {
    final transient MenuService menuService = new MenuService();
    final transient RestaurantService restaurantService = new RestaurantService();
    final transient RestaurantToMenuService restaurantToMenuService = new RestaurantToMenuService();
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        int menuId = Integer.parseInt(request.getParameter("menuId"));
        int restId = Integer.parseInt(request.getParameter("restId"));
        MenuDto mDto = menuService.getById(menuId);
        RestaurantsDto rDto = restaurantService.getById(restId);
        if (mDto != null && rDto != null) {
            try {
                restaurantToMenuService.save(menuId, restId);
                response.setStatus(HttpServletResponse.SC_CREATED);
            } catch (SQLException e) {
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                e.printStackTrace();
            }
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }
}
