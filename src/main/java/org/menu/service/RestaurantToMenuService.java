package org.menu.service;

import org.menu.repository.RestaurantMenuRepo;

import java.sql.SQLException;

public class RestaurantToMenuService {
    private RestaurantMenuRepo restaurantMenuRepo = new RestaurantMenuRepo();
    public RestaurantToMenuService() {}

    public void save(int menuID, int restaurantID) throws SQLException {
        restaurantMenuRepo.save(restaurantID, menuID);
    }
}
