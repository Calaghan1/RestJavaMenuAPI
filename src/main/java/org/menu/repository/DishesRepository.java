package org.menu.repository;

import org.menu.db.ConnectionManager;
import org.menu.model.Dishes;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DishesRepository {
    private final ConnectionManager connectionManager;

    public DishesRepository() {
        this.connectionManager = new ConnectionManager(); // Использует настройки по умолчанию
    }

    // Конструктор для тестирования или для другой конфигурации подключения
    public DishesRepository(ConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
    }


    public void initTable() throws SQLException {
        try (Connection con = connectionManager.getConnection();
             PreparedStatement ps = con.prepareStatement(SqlStatments.CREATE_DISH_TABLE.toString());) {
            ps.executeUpdate();
        }
    }

    public Dishes findById(int id) throws SQLException {
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SqlStatments.SELECT_WERE.toString().formatted("*", Dishes.tableName(), "id"));) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            Dishes dishesModel = new Dishes();
            if (resultSet.next()) {
                dishesModel.setId(resultSet.getInt("id"));
                dishesModel.setName(resultSet.getString("name"));
                dishesModel.setDescription(resultSet.getString("description"));
                dishesModel.setMenuId(resultSet.getInt("menu_id"));
                return dishesModel;
            }
        }
        return null;
    }


    public List<Dishes> findAll() throws SQLException {
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SqlStatments.SELECT.toString().formatted("*", Dishes.tableName()));) {
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Dishes> list = new ArrayList<>();
            while (resultSet.next()) {
                Dishes dishesModel = new Dishes();
                dishesModel.setId(resultSet.getInt("id"));
                dishesModel.setName(resultSet.getString("name"));
                dishesModel.setDescription(resultSet.getString("description"));
                dishesModel.setMenuId(resultSet.getInt("menu_id"));
                list.add(dishesModel);
            }
            return list;
        }
    }

    public List<Dishes> findAllByMenuID(int menu_id) throws SQLException {
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SqlStatments.SELECT_WERE.toString().formatted("*", Dishes.tableName(), "menu_id"));) {
            preparedStatement.setInt(1, menu_id);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Dishes> list = new ArrayList<>();
            while (resultSet.next()) {
                Dishes dishesModel = new Dishes();
                dishesModel.setId(resultSet.getInt("id"));
                dishesModel.setName(resultSet.getString("name"));
                dishesModel.setDescription(resultSet.getString("description"));
                dishesModel.setMenuId(resultSet.getInt("menu_id"));
                list.add(dishesModel);
            }
            return list;
        }
    }

    public Dishes save(Dishes dishesModel) throws SQLException {
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SqlStatments.INSERT.toString().formatted(Dishes.tableName(), "name, description, menu_id", "?, ?, ?"),
                     Statement.RETURN_GENERATED_KEYS);) {
            preparedStatement.setString(1, dishesModel.getName());
            preparedStatement.setString(2, dishesModel.getDescription());
            preparedStatement.setInt(3, dishesModel.getMenuId());
            int resultSet = preparedStatement.executeUpdate();
            if (resultSet > 0) {
                return dishesModel;
            }
        }
        return null;
    }

    public Dishes update(Dishes dishesModel, int id) throws SQLException {
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SqlStatments.UPDATE.toString().formatted(
                     "dishes", "name = ?, description = ?, menu_id = ?", "id = ?"));) {
            preparedStatement.setString(1, dishesModel.getName());
            preparedStatement.setString(2, dishesModel.getDescription());
            preparedStatement.setInt(3, dishesModel.getMenuId());
            preparedStatement.setInt(4, id);
            preparedStatement.executeUpdate();
            return dishesModel;
        }
    }

    public boolean delete(int id) throws SQLException {
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SqlStatments.DELETE.toString().formatted(Dishes.tableName(), "id = ?"));) {
            preparedStatement.setInt(1, id);
            int resultSet = preparedStatement.executeUpdate();
            if (resultSet > 0) {
                return true;
            }
        }
        return false;
    }

    public void dropTable() throws SQLException {
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SqlStatments.DROP_TABLE.toString().formatted(Dishes.tableName()));) {
            preparedStatement.executeUpdate();
        }
    }
}
