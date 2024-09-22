package org.menu.repository;

import org.menu.db.ConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RestaurantMenuRepo {
    ConnectionManager connectionManager;

    public RestaurantMenuRepo() {
        connectionManager = new ConnectionManager();
    }

    public RestaurantMenuRepo(ConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
    }

    public void init() throws SQLException {
        try (Connection con = connectionManager.getConnection();
             PreparedStatement ps2 = con.prepareStatement(SqlStatments.CREATE_RESTAURANT_TO_MENU_TABLE.toString());) {
            ps2.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean save(int restaurantId, int menuId) throws SQLException {
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SqlStatments.INSERT.toString().formatted("restaurants_menus", "restaurant_id, menu_id", "?, ?"));) {
            preparedStatement.setInt(1, restaurantId);
            preparedStatement.setInt(2, menuId);
            int resultSet = preparedStatement.executeUpdate();
            if (resultSet == 1) {
                return true;
            }
            return false;
        }
    }
}
