package org.menu.repository;

import org.menu.db.ConnectionManager;
import org.menu.model.Menu;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class MenuRepository {
    public final ConnectionManager connectionManager;

    public MenuRepository() {
        this.connectionManager = new ConnectionManager();
    }

    public MenuRepository(ConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
    }

    public void initTable() throws SQLException {
        try (
                Connection connection = connectionManager.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(SqlStatments.CREATE_MENU_TABLE.toString());
        ) {
            preparedStatement.executeUpdate();
        }
    }

    public Menu findById(int id) throws SQLException {
        try (
                Connection connection = connectionManager.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(SqlStatments.SELECT_WERE.toString().formatted("*", Menu.tableName(), "id"));
        ) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            Menu menu = new Menu();
            if (resultSet.next()) {
                menu.setId(resultSet.getInt("id"));
                menu.setName(resultSet.getString("name"));
                menu.setDescription(resultSet.getString("description"));
            } else {
                return null;
            }
            return menu;
        }
    }

    public List<Menu> findAll() throws SQLException {
        try (
                Connection connection = connectionManager.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(SqlStatments.SELECT.toString().formatted("*", Menu.tableName()));
        ) {
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Menu> list = new ArrayList<>();
            while (resultSet.next()) {
                Menu menuModel = new Menu();
                menuModel.setId(resultSet.getInt("id"));
                menuModel.setName(resultSet.getString("name"));
                menuModel.setDescription(resultSet.getString("description"));
                list.add(menuModel);
            }
            return list;
        }
    }

    public Menu save(Menu menuModel) throws SQLException {
        Menu menuSaved = null;
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SqlStatments.INSERT.toString().formatted(Menu.tableName(), "name ,description", "?, ?"),
                     Statement.RETURN_GENERATED_KEYS);) {

            preparedStatement.setString(1, menuModel.getName());
            preparedStatement.setString(2, menuModel.getDescription());
            int resultSet = preparedStatement.executeUpdate();
            if (resultSet > 0) {
                menuSaved = menuModel;
            }
        }
        return menuSaved;
    }

    public Menu update(Menu updateModel, int menuId) throws SQLException {
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SqlStatments.UPDATE.toString().formatted(Menu.tableName(), "name = ?, description = ?", "id = ?"));) {
            preparedStatement.setString(1, updateModel.getName());
            preparedStatement.setString(2, updateModel.getDescription());
            preparedStatement.setInt(3, menuId);
            int resultSet = preparedStatement.executeUpdate();
            if (resultSet > 0) {
                return updateModel;
            } else {
                return null;
            }
        }

    }

    public boolean delete(int id) throws SQLException {
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SqlStatments.DELETE.toString().formatted(Menu.tableName(), "id = ?"));) {
            preparedStatement.setInt(1, id);
            int resultSet = preparedStatement.executeUpdate();
            if (resultSet > 0) {
                return true;
            }
        }
        return false;
    }

    public List<Menu> findMenuByRestaurantId(int restaurantId) throws SQLException {
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT m.id, m.name, m.description FROM menu m " +
                     "JOIN restaurants_menus rm ON m.id = rm.menu_id " +
                     "WHERE rm.restaurant_id = ?");) {

            preparedStatement.setInt(1, restaurantId);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Menu> list = new ArrayList<>();
            while (resultSet.next()) {
                Menu menuModel = new Menu();
                menuModel.setId(resultSet.getInt("id"));
                menuModel.setName(resultSet.getString("name"));
                menuModel.setDescription(resultSet.getString("description"));
                list.add(menuModel);
            }
            return list;
        }
    }

    public void dropTable() throws SQLException {
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SqlStatments.DROP_TABLE_CASCADE.toString().formatted(Menu.tableName()))) {
            preparedStatement.executeUpdate();
        }
    }
}
