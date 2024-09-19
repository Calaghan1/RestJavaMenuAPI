package org.menu.service;

import org.menu.repository.RestaurantMenuRepo;

import java.sql.SQLException;

public class RestaurantToMenuService {
    private RestaurantMenuRepo restaurantMenuRepo;
    public RestaurantToMenuService() {
       this.restaurantMenuRepo = new RestaurantMenuRepo();
    }
    public RestaurantToMenuService(RestaurantMenuRepo restaurantMenuRepo) {
        this.restaurantMenuRepo = restaurantMenuRepo;
    }

    public boolean save(int menuID, int restaurantID) {
        try {
            return restaurantMenuRepo.save(restaurantID, menuID);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
