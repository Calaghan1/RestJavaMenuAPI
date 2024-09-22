package org.menu.servlet;


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


@WebServlet(name = "ConnectMenuAndRestServlet", value = "/connect")
public class ConnectMenuAndRestServlet extends HttpServlet {
    private final transient MenuService menuService;
    private final transient RestaurantService restaurantService;
    private final transient RestaurantToMenuService restaurantToMenuService;

    public ConnectMenuAndRestServlet() {
        this.menuService = new MenuService();
        this.restaurantService = new RestaurantService();
        this.restaurantToMenuService = new RestaurantToMenuService();
    }

    public ConnectMenuAndRestServlet(MenuService menuService, RestaurantService restaurantService,
                                     RestaurantToMenuService restaurantToMenuService) {
        this.menuService = menuService;
        this.restaurantService = restaurantService;
        this.restaurantToMenuService = restaurantToMenuService;
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        try {
            int menuId = Integer.parseInt(request.getParameter("menuId"));
            int restId = Integer.parseInt(request.getParameter("restId"));
            MenuDto menuDto = menuService.getById(menuId);
            RestaurantsDto restaurantsDto = restaurantService.getById(restId);
            if (menuDto != null && restaurantsDto != null) {
                if (restaurantToMenuService.save(menuId, restId)) {
                    response.setStatus(HttpServletResponse.SC_CREATED);
                    response.getWriter().write("Done");
                } else {
                    response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                    response.getWriter().write("Failed");
                }
            } else {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                response.getWriter().write("No such Menu or Restaurant found");
            }
        } catch (NumberFormatException | IOException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }
}
