package org.menu.servlet;

import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.menu.repository.RestaurantsRepository;
import org.menu.service.RestaurantService;
import org.menu.service.RestaurantToMenuService;
import org.menu.servlet.dto.RestaurantsDto;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "RestorauntToMenuServlet", value = "rest_to_menu")
public class RestorauntToMenuServlet extends HttpServlet {
    private transient RestaurantToMenuService service;
    private transient RestaurantService restService;
    public RestorauntToMenuServlet() {
        service = new RestaurantToMenuService();
        restService = new RestaurantService();
    };
    public RestorauntToMenuServlet (RestaurantToMenuService service, RestaurantService restService) {
        this.service = service;
        this.restService = restService;
    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        int id = Integer.parseInt(request.getParameter("menuID"));
        List<RestaurantsDto> dto = restService.getAllRestaurantsByMenuID(id);
        if (dto == null ) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        } else {
            Gson gson = new Gson();
            String json = gson.toJson(dto);
            response.getWriter().write(json);
        }
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        int menuID = Integer.parseInt(request.getParameter("menuID"));
        int restaurantID = Integer.parseInt(request.getParameter("restaurantID"));
        if (service.save(menuID, restaurantID)) {
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write("Done");
        } else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("Failed");
        }
    }
}
