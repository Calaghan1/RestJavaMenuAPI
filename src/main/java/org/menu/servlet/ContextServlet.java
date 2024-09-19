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
    Logger log = Logger.getLogger(ContextServlet.class.getName());
    DishesRepository dishesRepository;
    MenuRepository menuRepository;
    RestaurantsRepository restaurantsRepository;
    RestaurantMenuRepo restaurantMenuRepo;
    ConnectionManager connectionManager;
    public ContextServlet() {
        this.dishesRepository = new DishesRepository();
        this.menuRepository = new MenuRepository();
        this.restaurantsRepository = new RestaurantsRepository();
        this.restaurantMenuRepo = new RestaurantMenuRepo();
        this.connectionManager = new ConnectionManager();
    }
    public ContextServlet(DishesRepository dishesRepository, MenuRepository menuRepository, RestaurantsRepository restaurantsRepository,
                          RestaurantMenuRepo restaurantMenuRepo, ConnectionManager connectionManager) {
        this.dishesRepository = dishesRepository;
        this.menuRepository = menuRepository;
        this.restaurantsRepository = restaurantsRepository;
        this.restaurantMenuRepo = restaurantMenuRepo;
        this.connectionManager = connectionManager;
    }
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        try {
            connectionManager.init();
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
