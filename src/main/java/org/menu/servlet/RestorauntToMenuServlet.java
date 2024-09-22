package org.menu.servlet;

import com.google.gson.Gson;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.menu.service.RestaurantService;
import org.menu.service.RestaurantToMenuService;
import org.menu.servlet.dto.RestaurantsDto;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "RestorauntToMenuServlet", value = "rest_to_menu")
public class RestorauntToMenuServlet extends HttpServlet {
    private final transient RestaurantToMenuService service;
    private final transient RestaurantService restService;

    public RestorauntToMenuServlet() {
        service = new RestaurantToMenuService();
        restService = new RestaurantService();
    }

    public RestorauntToMenuServlet(RestaurantToMenuService service, RestaurantService restService) {
        this.service = service;
        this.restService = restService;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        try {
            int id = Integer.parseInt(request.getParameter("menuID"));
            List<RestaurantsDto> restaurantsDtoList = restService.getAllRestaurantsByMenuID(id);
            if (restaurantsDtoList == null) {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            } else {
                Gson gson = new Gson();
                String json = gson.toJson(restaurantsDtoList);
                response.getWriter().write(json);
            }
        } catch (NumberFormatException | IOException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        try {
            int menuID = Integer.parseInt(request.getParameter("menuID"));
            int restaurantID = Integer.parseInt(request.getParameter("restaurantID"));
            if (service.save(menuID, restaurantID)) {
                response.setStatus(HttpServletResponse.SC_OK);
                response.getWriter().write("Done");
            } else {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write("Failed");
            }
        } catch (NumberFormatException | IOException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }
}
