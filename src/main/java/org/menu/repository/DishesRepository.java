package org.menu.repository;

import org.menu.db.ConnectionManager;
import org.menu.model.Dishes;
import java.util.logging.Logger;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DishesRepository {
    Logger logger = Logger.getLogger("DishesRepository");
    private final ConnectionManager cm;
    public DishesRepository() {
        this.cm = new ConnectionManager(); // Использует настройки по умолчанию
    }
    // Конструктор для тестирования или для другой конфигурации подключения
    public DishesRepository(ConnectionManager cm) {
        this.cm = cm;
    }


    public void initTable() throws SQLException {
        try ( Connection con = cm.getConnection();
              PreparedStatement ps = con.prepareStatement(SqlStatments.CREATE_DISH_TABLE.toString());) {
            ps.executeUpdate();
        }
    }
    public Dishes findById(int id) throws SQLException {
        try (Connection con = cm.getConnection();
             PreparedStatement ps = con.prepareStatement(SqlStatments.SELECT_WERE.toString().formatted("*", "dishes", "id"));) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            Dishes dishesModel = new Dishes();
            if (rs.next()) {
                dishesModel.setId(rs.getInt("id"));
                dishesModel.setName(rs.getString("name"));
                dishesModel.setDescription(rs.getString("description"));
                dishesModel.setMenuId(rs.getInt("menu_id"));
                return dishesModel;
            }
        }
        return null;
    }


    public List<Dishes> findAll() throws SQLException {
        try ( Connection con = cm.getConnection();
              PreparedStatement ps = con.prepareStatement(SqlStatments.SELECT.toString().formatted("*", "dishes"));) {
            ResultSet rs = ps.executeQuery();
            List<Dishes> list = new ArrayList<>();
            while (rs.next()) {
                Dishes dishesModel = new Dishes();
                dishesModel.setId(rs.getInt("id"));
                dishesModel.setName(rs.getString("name"));
                dishesModel.setDescription(rs.getString("description"));
                dishesModel.setMenuId(rs.getInt("menu_id"));
                list.add(dishesModel);
            }
            return list;
        }
    }
    public List<Dishes> findAllByMenuID(int menu_id) throws SQLException {
        try (Connection con = cm.getConnection();
             PreparedStatement ps = con.prepareStatement(SqlStatments.SELECT_WERE.toString().formatted("*", "dishes", "menu_id"));) {
            ps.setInt(1, menu_id);
            ResultSet rs = ps.executeQuery();
            List<Dishes> list = new ArrayList<>();
            while (rs.next()) {
                Dishes dishesModel = new Dishes();
                dishesModel.setId(rs.getInt("id"));
                dishesModel.setName(rs.getString("name"));
                dishesModel.setDescription(rs.getString("description"));
                dishesModel.setMenuId(rs.getInt("menu_id"));
                list.add(dishesModel);
            }
            return list;
        }
    }

    public Dishes save(Dishes dishesModel) throws SQLException {
        try (            Connection con = cm.getConnection();
                         PreparedStatement ps = con.prepareStatement(SqlStatments.INSERT.toString().formatted("dishes", "name, description, menu_id", "?, ?, ?"),
                                 Statement.RETURN_GENERATED_KEYS);) {
            ps.setString(1, dishesModel.getName());
            ps.setString(2, dishesModel.getDescription());
            ps.setInt(3, dishesModel.getMenuId());
            int rs = ps.executeUpdate();
            if (rs > 0) {
                return dishesModel;
            }
        }
        return null;
    }
    public Dishes update(Dishes dishesModel, int id) throws SQLException {
        try (Connection con = cm.getConnection();
             PreparedStatement ps = con.prepareStatement(SqlStatments.UPDATE.toString().formatted(
                                 "dishes", "name = ?, description = ?, menu_id = ?", "id = ?"));) {
            ps.setString(1, dishesModel.getName());
            ps.setString(2, dishesModel.getDescription());
            ps.setInt(3, dishesModel.getMenuId());
            ps.setInt(4, id);
            ps.executeUpdate();
            return dishesModel;
        }
    }
    public boolean delete(int id) throws SQLException {
        try(Connection con = cm.getConnection();
            PreparedStatement ps = con.prepareStatement(SqlStatments.DELETE.toString().formatted("dishes", "id = ?"));) {
            ps.setInt(1, id);
            int rs = ps.executeUpdate();
            if (rs > 0) {
                return true;
            }
        }
        return false;
    }
    public void dropTable() throws SQLException{
        try (Connection con = cm.getConnection();
             PreparedStatement ps = con.prepareStatement(SqlStatments.DROP_TABLE.toString().formatted("dishes"));) {
            ps.executeUpdate();
        }
    }
}
