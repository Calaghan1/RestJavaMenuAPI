package org.menu.servlet;


import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import org.menu.db.ConnectionManager;
import org.menu.repository.DishesRepository;
import org.menu.repository.MenuRepository;
import jakarta.servlet.annotation.WebListener;
import org.menu.repository.RestaurantMenuRepo;
import org.menu.repository.RestaurantsRepository;

import java.sql.SQLException;
import java.util.logging.Logger;


@WebListener
public class ContextServlet implements  ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        Logger log = Logger.getLogger(ContextServlet.class.getName());
        DishesRepository dishesRepository = new DishesRepository();
        MenuRepository menuRepository = new MenuRepository();
        RestaurantsRepository restaurantsRepository = new RestaurantsRepository();
        RestaurantMenuRepo restaurantMenuRepo = new RestaurantMenuRepo();
        try {
            ConnectionManager.init();
            menuRepository.initTable();
            dishesRepository.initTable();
            restaurantsRepository.initTable();
            restaurantMenuRepo.init();
        } catch (SQLException e) {
            log.severe("Error initializing DB\n" + e.getMessage());
        }
        log.info("_______________________Context initialized_______________________");
    }
}
