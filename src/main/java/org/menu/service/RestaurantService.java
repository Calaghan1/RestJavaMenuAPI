package org.menu.service;


import org.menu.model.Restaurants;
import org.menu.repository.RestaurantsRepository;
import org.menu.repository.mappers.RestaurantsInterface;
import org.menu.servlet.dto.RestaurantsDto;

import java.util.List;
import java.util.logging.Logger;

public class RestaurantService {
    Logger logger = Logger.getLogger(RestaurantService.class.getName());
    private final RestaurantsRepository rep;
    private final RestaurantsInterface mapper = RestaurantsInterface.INSTANCE;

    public RestaurantService() {
        this.rep = new RestaurantsRepository();
    }
    public RestaurantService(final RestaurantsRepository rep) {
        this.rep = rep;
    }
    public RestaurantsDto getById(int i) {
        try {
            Restaurants rest = rep.findById(i);
            return mapper.toDto(rest);
        } catch (Exception e) {
            logger.severe("Error in " + this.getClass().getName() + ": " + e.getMessage());
            return null;
        }

    }
    public List<RestaurantsDto> getAll() {
        try {
            List<Restaurants> rest = rep.findAll();
            return mapper.toDoListEntity(rest);
        } catch (Exception e) {
            logger.severe("Error in " + this.getClass().getName() + ": " + e.getMessage());
            return null;
        }

    }
    public RestaurantsDto save(RestaurantsDto dto) {
        try {
            Restaurants resp = rep.save(mapper.toEntity(dto));
            return mapper.toDto(resp);
        } catch (Exception e) {
            logger.severe("Error in " + this.getClass().getName() + ": " + e.getMessage());
            return null;
        }

    }
    public RestaurantsDto update(RestaurantsDto dto, int id) {
        try {
            Restaurants resp = rep.update(mapper.toEntity(dto), id);
            return mapper.toDto(resp);
        } catch (Exception e) {
            logger.severe("Error in " + this.getClass().getName() + ": " + e.getMessage());
            return null;
        }

    }
    public boolean delete(int id) {
        try {
            return  rep.delete(id);
        } catch (Exception e) {
            logger.severe("Error in " + this.getClass().getName() + ": " + e.getMessage());
            return false;
        }

    }
    public List<RestaurantsDto> getAllRestaurantsByMenuID(int menuID) {
        try {
            List<Restaurants> rest = rep.findRestByMenuID(menuID);
            return  mapper.toDoListEntity(rest);
        } catch (Exception e) {
            logger.severe("Error in " + this.getClass().getName() + ": " + e.getMessage());
            return null;
        }

    }
}
