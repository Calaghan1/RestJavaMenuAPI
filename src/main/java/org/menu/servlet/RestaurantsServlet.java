package org.menu.servlet;

import com.google.gson.Gson;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.menu.service.RestaurantService;
import org.menu.servlet.dto.RestaurantsDto;

import java.io.IOException;
import java.util.List;

@WebServlet(name="RestaurantServlet", value = {"/restaurant", "/restAll"})
public class RestaurantsServlet extends HttpServlet {
    private final transient RestaurantService service;
    public RestaurantsServlet() {
        this.service = new RestaurantService();
    }
    public RestaurantsServlet(RestaurantService service) {
        this.service = service;
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)  {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        try {
            if (request.getServletPath().equals("/restaurant")) { // b
                int id = Integer.parseInt(request.getParameter("id"));
                RestaurantsDto dto = service.getById(id);
                if (dto == null) {
                    response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    response.getWriter().println("Not found");
                } else {
                    Gson gson = new Gson();
                    String json = gson.toJson(dto);
                    response.getWriter().write(json);
                }
            } else if (request.getServletPath().equals("/restAll")) {
                List<RestaurantsDto> dtos = service.getAll();
                Gson gson = new Gson();
                String json = gson.toJson(dtos);
                response.getWriter().write(json);
            }
        }catch (NumberFormatException | IOException  e) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            }

    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)  {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        try {
            StringBuilder body = new StringBuilder();
            String line;
            while ((line = request.getReader().readLine()) != null) {
                body.append(line);
            }
            Gson gson = new Gson();
            RestaurantsDto dto = gson.fromJson(body.toString(), RestaurantsDto.class);
            dto = service.save(dto);
            if (dto == null) {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                response.getWriter().println("Internal server error");
            } else {
                response.setStatus(HttpServletResponse.SC_OK);
                response.getWriter().write("{\"message\": \"Menu created successfully\"}");
            }
        } catch (NumberFormatException | IOException  e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }
    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response)  {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            StringBuilder body = new StringBuilder();
            String line;
            while ((line = request.getReader().readLine()) != null) {
                body.append(line);
            }
            Gson gson = new Gson();
            RestaurantsDto dto = gson.fromJson(body.toString(), RestaurantsDto.class);
            dto = service.update(dto, id);
            if (dto == null) {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                response.getWriter().println("Internal server error");
            } else {
                response.setStatus(HttpServletResponse.SC_OK);
                response.getWriter().write("{\"message\": \"Restaurant updated successfully\"}");
            }
        } catch (NumberFormatException | IOException  e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }
    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)  {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            if (service.delete(id)) {
                response.setStatus(HttpServletResponse.SC_OK);
                response.getWriter().write("{\"message\": \"Restaurant deleted successfully\"}");
            } else {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                response.getWriter().println("Internal server error");
            }
        } catch (NumberFormatException | IOException  e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }
}
