package org.menu.repository;

import org.menu.db.ConnectionManager;
import org.menu.servlet.dto.RestaurantsDto;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RestaurantMenuRepo  {
    ConnectionManager cm;
    public RestaurantMenuRepo() {
        cm = new ConnectionManager();
    }
    public RestaurantMenuRepo(ConnectionManager cm) {
        this.cm = cm;
    }
    public void init() throws SQLException {
        try( Connection con = cm.getConnection();
             PreparedStatement ps2 = con.prepareStatement(SqlStatments.CREATE_RESTAURANT_TO_MENU_TABLE.toString());) {
            ps2.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void save(int restaurantId, int menuId) throws  SQLException{
        try ( Connection con = cm.getConnection();
              PreparedStatement ps = con.prepareStatement(SqlStatments.INSERT.toString().formatted("restaurants_menus" , "restaurant_id, menu_id", "?, ?"));) {
            ps.setInt(1, restaurantId);
            ps.setInt(2, menuId);
            ps.executeUpdate();
        }
    }
}
