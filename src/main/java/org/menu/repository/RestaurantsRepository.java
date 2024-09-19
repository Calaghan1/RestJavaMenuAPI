package org.menu.repository;

import org.menu.db.ConnectionManager;
import org.menu.model.Restaurants;
import org.menu.servlet.dto.RestaurantsDto;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RestaurantsRepository {
    public final ConnectionManager cm;
    public RestaurantsRepository() {
        this.cm = new ConnectionManager();
    }
    public RestaurantsRepository(ConnectionManager cm) {this.cm = cm;}
    public void initTable()  throws SQLException {
        try ( Connection con = cm.getConnection();
              PreparedStatement ps = con.prepareStatement(SqlStatments.CREATE_RESTAURANT_TABLE.toString())) {
            ps.executeUpdate();
        }
    }
    public Restaurants findById(int id) throws SQLException{
        try(Connection con = cm.getConnection();
            PreparedStatement ps = con.prepareStatement(SqlStatments.SELECT_WERE.toString().formatted("*", "restaurants", "id"));)
            {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
                Restaurants rest = new Restaurants();
            if (rs.next()) {
                rest.setId(rs.getInt("id"));
                rest.setName(rs.getString("name"));
                return rest;
            } else {
                return null;
            }
        }

    }
    public List<Restaurants> findAll() throws SQLException{
        try(Connection con = cm.getConnection();
            PreparedStatement ps = con.prepareStatement(SqlStatments.SELECT.toString().formatted("*", "restaurants"));) {
            ResultSet rs = ps.executeQuery();
            List<Restaurants> list = new ArrayList<>();
            while (rs.next()) {
                Restaurants restaurantsModel = new Restaurants();
                restaurantsModel.setId(rs.getInt("id"));
                restaurantsModel.setName(rs.getString("name"));
                list.add(restaurantsModel);
            }
            return list;
        }

    }
    public Restaurants save(Restaurants dto) throws SQLException{
        Restaurants rest = null;
        try (Connection con = cm.getConnection();
             PreparedStatement ps = con.prepareStatement(SqlStatments.INSERT.toString().formatted("restaurants", "name", "?",
                                 Statement.RETURN_GENERATED_KEYS));) {
            ps.setString(1, dto.getName());
            int rs = ps.executeUpdate();
            if (rs > 0) {
                rest =  dto;
            }
        }
        return rest;
    }
    public Restaurants update(Restaurants restaurantsModel, int id) throws SQLException{
        try(Connection con = cm.getConnection();
            PreparedStatement ps = con.prepareStatement( SqlStatments.UPDATE.toString().formatted("restaurants", "name = ?", "id = ?"));)
             {
            ps.setString(1, restaurantsModel.getName());
            ps.setInt(2, id);
            ps.executeUpdate();
            return restaurantsModel;
        }
    }
    public boolean delete(int id) throws SQLException{
        try ( Connection con = cm.getConnection();
              PreparedStatement ps = con.prepareStatement(SqlStatments.DELETE.toString().formatted("restaurants", "id = ?"));)
              {
            ps.setInt(1, id);
            int rs = ps.executeUpdate();
            if (rs > 0) {
                return true;
            }
        }
        return false;
    }
    public List<Restaurants> findRestByMenuID(int menuId) throws SQLException {
        try (Connection con = cm.getConnection();
             PreparedStatement ps = con.prepareStatement("SELECT r.id, r.name FROM restaurants r " +
                                 "JOIN restaurants_menus rm ON r.id = rm.restaurant_id " +
                                 "WHERE rm.menu_id = ?");)
        {
            ps.setInt(1, menuId);
            ResultSet rs = ps.executeQuery();
            List<Restaurants> list = new ArrayList<>();
            while (rs.next()) {
                Restaurants restaurantsModel = new Restaurants();
                restaurantsModel.setId(rs.getInt("id"));
                restaurantsModel.setName(rs.getString("name"));
                list.add(restaurantsModel);
            }
            return list;
        }

    }
    public void dropTable() throws SQLException {
        try (Connection con = cm.getConnection();
             PreparedStatement ps = con.prepareStatement(SqlStatments.DROP_TABLE_CASCADE.toString().formatted("restaurants"))) {
            ps.executeUpdate();
        }
    }

}
