package org.menu.service;

import org.menu.repository.RestaurantMenuRepo;

import java.sql.SQLException;
import java.util.logging.Logger;

public class RestaurantToMenuService {
    Logger logger = Logger.getLogger(RestaurantService.class.getName());
    private final RestaurantMenuRepo restaurantMenuRepo;
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
            logger.severe(ErrorHandler.errorMassage(this.getClass().getName(), e));
        }
        return false;
    }
}
