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
import java.util.Objects;


@WebServlet(name = "MenuServlet", value = {"/menu", "/menuall"})
public class MenuServlet extends HttpServlet {
    private transient MenuService service;
    public MenuServlet(){
        this.service = new MenuService();
    }
    public MenuServlet(MenuService service){
        this.service = service;
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        if (Objects.equals(request.getServletPath(), "/menu")) {
            int id = Integer.parseInt(request.getParameter("id"));
            MenuDto menuData = service.getById(id);
            if (menuData == null) {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                response.getWriter().println("{'message':'Menu not found.'}");
            } else {
                Gson gson = new Gson();
                String jsonResponse = gson.toJson(menuData);
                response.getWriter().write(jsonResponse);
            }
        } else if (Objects.equals(request.getServletPath(), "/menuall")) {
            List<MenuDto> menuAll;
            menuAll = service.getAll();
            System.out.println(menuAll);
            Gson gson = new Gson();
            String jsonResponse = gson.toJson(menuAll);
            response.getWriter().write(jsonResponse);
        }
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        StringBuilder body = new StringBuilder();
        String line;
        while ((line = request.getReader().readLine()) != null) {
            System.out.println(line);
            body.append(line);
        }
        Gson gson = new Gson();
        MenuDto menu = gson.fromJson(body.toString(), MenuDto.class);
        menu = service.save(menu);
        if (menu == null) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"message\": \"Error saving new menu\"}");
        } else {
            response.setStatus(HttpServletResponse.SC_CREATED);
            response.getWriter().write("{\"message\": \"Menu created successfully\"}");
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        int id = Integer.parseInt(req.getParameter("id"));
        if (service.delete(id)) {
            resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
            resp.getWriter().write("{\"message\": \"Menu deleted successfully\"}");
        } else {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write("{\"message\": \"No such menu exists\"}");
        }
    }
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        int id = Integer.parseInt(req.getParameter("id"));
        StringBuilder body = new StringBuilder();
        String line;
        while ((line = req.getReader().readLine()) != null) {
            body.append(line);
        }
        Gson gson = new Gson();
        MenuDto menu = gson.fromJson(body.toString(), MenuDto.class);
        menu = service.update(menu, id);
        if (menu == null) {
            resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
            resp.getWriter().write("{\"message\": \"Error saving new menu\"}");
        } else {
            resp.setStatus(HttpServletResponse.SC_OK);
            resp.getWriter().write("{\"message\": \"Menu updated successfully\"}");
        }
    }
}
