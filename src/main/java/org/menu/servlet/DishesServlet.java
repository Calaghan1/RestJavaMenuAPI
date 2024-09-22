package org.menu.servlet;


import com.google.gson.Gson;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.menu.service.DishesService;
import org.menu.servlet.dto.DishesDto;


import java.io.IOException;
import java.util.List;
import java.util.Objects;

@WebServlet(name = "DishesServlet", value = {"/dish", "/dishes"})
public class DishesServlet extends HttpServlet {
    private final transient DishesService service;

    public DishesServlet() {
        this.service = new DishesService();
    }

    public DishesServlet(DishesService service) {
        this.service = service;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        try {
            if (Objects.equals(request.getServletPath(), "/dish")) {
                int id = Integer.parseInt(request.getParameter("id"));
                DishesDto menuData = service.getById(id);
                if (menuData == null) {
                    response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    response.getWriter().println("Not found");
                } else {
                    Gson gson = new Gson();
                    String jsonResponse = gson.toJson(menuData);
                    response.getWriter().write(jsonResponse);
                }
            } else if (Objects.equals(request.getServletPath(), "/dishes")) {
                List<DishesDto> menuAll;
                menuAll = service.getAll();
                Gson gson = new Gson();
                String jsonResponse = gson.toJson(menuAll);
                response.getWriter().write(jsonResponse);

            }
        } catch (NumberFormatException | IOException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        StringBuilder body = new StringBuilder();
        String line;
        try {
            while ((line = request.getReader().readLine()) != null) {
                body.append(line);
            }
            Gson gson = new Gson();
            DishesDto dishes = gson.fromJson(body.toString(), DishesDto.class);
            dishes = service.save(dishes);
            if (dishes == null) {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                response.getWriter().write("{\"message\": \"Error saving new Dish\"}");
            } else {
                response.setStatus(HttpServletResponse.SC_CREATED);
                response.getWriter().write("{\"message\": \"Dish created successfully\"}");
            }
        } catch (NumberFormatException | IOException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) {
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
            DishesDto dishes = gson.fromJson(body.toString(), DishesDto.class);
            dishes = service.update(dishes, id);
            if (dishes == null) {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                response.getWriter().println("Not found");
            } else {
                response.setStatus(HttpServletResponse.SC_OK);
                response.getWriter().write("{\"message\": \"Dish updated successfully\"}");
            }
        } catch (NumberFormatException | IOException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            if (service.delete(id)) {
                response.setStatus(HttpServletResponse.SC_NO_CONTENT);
                response.getWriter().write("{\"message\": \"Dish deleted successfully\"}");
            } else {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                response.getWriter().write("{\"message\": \"Dish not found\"}");
            }
        } catch (NumberFormatException | IOException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }
}
