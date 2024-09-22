package org.menu.repository;

import org.menu.db.ConnectionManager;
import org.menu.model.Restaurants;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RestaurantsRepository {
    public final ConnectionManager connectionManager;

    public RestaurantsRepository() {
        this.connectionManager = new ConnectionManager();
    }

    public RestaurantsRepository(ConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
    }

    public void initTable() throws SQLException {
        try (Connection con = connectionManager.getConnection();
             PreparedStatement ps = con.prepareStatement(SqlStatments.CREATE_RESTAURANT_TABLE.toString())) {
            ps.executeUpdate();
        }
    }

    public Restaurants findById(int id) throws SQLException {
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SqlStatments.SELECT_WERE.toString().formatted("*", Restaurants.tableName(), "id"));) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            Restaurants rest = new Restaurants();
            if (resultSet.next()) {
                rest.setId(resultSet.getInt("id"));
                rest.setName(resultSet.getString("name"));
                return rest;
            } else {
                return null;
            }
        }

    }

    public List<Restaurants> findAll() throws SQLException {
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SqlStatments.SELECT.toString().formatted("*", Restaurants.tableName()));) {
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Restaurants> list = new ArrayList<>();
            while (resultSet.next()) {
                Restaurants restaurantsModel = new Restaurants();
                restaurantsModel.setId(resultSet.getInt("id"));
                restaurantsModel.setName(resultSet.getString("name"));
                list.add(restaurantsModel);
            }
            return list;
        }

    }

    public Restaurants save(Restaurants dto) throws SQLException {
        Restaurants restaurants = null;
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SqlStatments.INSERT.toString().formatted(Restaurants.tableName(), "name", "?",
                     Statement.RETURN_GENERATED_KEYS));) {
            preparedStatement.setString(1, dto.getName());
            int resultSet = preparedStatement.executeUpdate();
            if (resultSet > 0) {
                restaurants = dto;
            }
        }
        return restaurants;
    }

    public Restaurants update(Restaurants restaurantsModel, int id) throws SQLException {
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SqlStatments.UPDATE.toString().formatted(Restaurants.tableName(), "name = ?", "id = ?"));) {
            preparedStatement.setString(1, restaurantsModel.getName());
            preparedStatement.setInt(2, id);
            preparedStatement.executeUpdate();
            return restaurantsModel;
        }
    }

    public boolean delete(int id) throws SQLException {
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SqlStatments.DELETE.toString().formatted(Restaurants.tableName(), "id = ?"));) {
            preparedStatement.setInt(1, id);
            int resultSet = preparedStatement.executeUpdate();
            if (resultSet > 0) {
                return true;
            }
        }
        return false;
    }

    public List<Restaurants> findRestByMenuID(int menuId) throws SQLException {
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT r.id, r.name FROM restaurants r " +
                     "JOIN restaurants_menus rm ON r.id = rm.restaurant_id " +
                     "WHERE rm.menu_id = ?");) {
            preparedStatement.setInt(1, menuId);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Restaurants> list = new ArrayList<>();
            while (resultSet.next()) {
                Restaurants restaurantsModel = new Restaurants();
                restaurantsModel.setId(resultSet.getInt("id"));
                restaurantsModel.setName(resultSet.getString("name"));
                list.add(restaurantsModel);
            }
            return list;
        }

    }

    public void dropTable() throws SQLException {
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SqlStatments.DROP_TABLE_CASCADE.toString().formatted(Restaurants.tableName()))) {
            preparedStatement.executeUpdate();
        }
    }

}
