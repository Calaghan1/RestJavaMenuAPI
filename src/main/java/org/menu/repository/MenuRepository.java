package org.menu.repository;

import org.menu.db.ConnectionManager;
import org.menu.model.Menu;
import org.menu.servlet.dto.DishesDto;
import org.menu.servlet.dto.MenuDto;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
public class MenuRepository {
    Logger logger = Logger.getLogger("MenuRepository");
    public final ConnectionManager  cm;
    public MenuRepository() {this.cm = new ConnectionManager();}
    public MenuRepository(ConnectionManager cm) {this.cm = cm;}
    public void initTable() throws SQLException {
        try(
                Connection con = cm.getConnection();
                PreparedStatement ps = con.prepareStatement(SqlStatments.CREATE_MENU_TABLE.toString());
                ) {
            ps.executeUpdate();
        }
    }
    public Menu findById(int id) throws SQLException{
        try(
                Connection con = cm.getConnection();
                PreparedStatement ps = con.prepareStatement(SqlStatments.SELECT_WERE.toString().formatted("*", "menu", "id"));
                ) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            Menu menu = new Menu();
            if (rs.next()) {
                menu.setId(rs.getInt("id"));
                menu.setName(rs.getString("name"));
                menu.setDescription(rs.getString("description"));
            } else {
                return null;
            }
            return menu;
        }
    }
    public List<Menu> findAll() throws SQLException {
        try(
                Connection con = cm.getConnection();
                PreparedStatement ps = con.prepareStatement(SqlStatments.SELECT.toString().formatted("*", "menu"));
                ) {
            ResultSet rs = ps.executeQuery();
            List<Menu> list = new ArrayList<>();
            while (rs.next()) {
                Menu menuModel = new Menu();
                menuModel.setId(rs.getInt("id"));
                menuModel.setName(rs.getString("name"));
                menuModel.setDescription(rs.getString("description"));
                list.add(menuModel);
            }
            return list;
        }
    }
    public Menu save(Menu menuModel) throws SQLException {
        Menu menuSaved = null;
        try( Connection con = cm.getConnection();
             PreparedStatement ps = con.prepareStatement(SqlStatments.INSERT.toString().formatted("menu", "name ,description","?, ?"),
                     Statement.RETURN_GENERATED_KEYS);) {

            ps.setString(1, menuModel.getName());
            ps.setString(2, menuModel.getDescription());
            int rs = ps.executeUpdate();
            if (rs > 0) {
                menuSaved = menuModel;
            }
        }
        return menuSaved;
    }
    public Menu update(Menu updateModel, int menuId) throws SQLException {
        try (Connection con = cm.getConnection();
             PreparedStatement ps = con.prepareStatement(SqlStatments.UPDATE.toString().formatted("menu", "name = ?, description = ?", "id = ?"));) {
            ps.setString(1, updateModel.getName());
            ps.setString(2, updateModel.getDescription());
            ps.setInt(3, menuId);
            int res = ps.executeUpdate();
            if (res > 0) {
                return updateModel;
            } else {
                return null;
            }
        }

    }
    public boolean delete(int id) throws SQLException {
        try (Connection con = cm.getConnection();
             PreparedStatement ps = con.prepareStatement(SqlStatments.DELETE.toString().formatted("menu", "id = ?"));){
            ps.setInt(1, id);
            int rs = ps.executeUpdate();
            if (rs > 0) {
                return true;
            }
        }
        return false;
    }
    public List<Menu> findMenuByRestaurantId(int restaurantId) throws SQLException {
        try (            Connection con = cm.getConnection();
                         PreparedStatement ps = con.prepareStatement("SELECT m.id, m.name, m.description FROM menu m " +
                                 "JOIN restaurants_menus rm ON m.id = rm.menu_id " +
                                 "WHERE rm.restaurant_id = ?");) {

            ps.setInt(1, restaurantId);
            ResultSet rs = ps.executeQuery();
            List<Menu> list = new ArrayList<>();
            while (rs.next()) {
                Menu menuModel = new Menu();
                menuModel.setId(rs.getInt("id"));
                menuModel.setName(rs.getString("name"));
                menuModel.setDescription(rs.getString("description"));
                list.add(menuModel);
            }
            return list;
        }
    }
    public void dropTable() throws SQLException {
        try (Connection con = cm.getConnection();
             PreparedStatement ps = con.prepareStatement(SqlStatments.DROP_TABLE_CASCADE.toString().formatted("menu"))) {
            ps.executeUpdate();
        }
    }
}
