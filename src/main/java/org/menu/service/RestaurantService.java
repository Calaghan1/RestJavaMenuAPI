package org.menu.service;


import org.menu.model.Restaurants;
import org.menu.repository.RestaurantsRepository;
import org.menu.repository.mappers.RestaurantsInterface;
import org.menu.servlet.dto.RestaurantsDto;

import java.util.List;
import java.util.logging.Logger;

public class RestaurantService {
    Logger logger = Logger.getLogger(RestaurantService.class.getName());
    private final RestaurantsRepository repository;
    private static final RestaurantsInterface mapper = RestaurantsInterface.INSTANCE;

    public RestaurantService() {
        this.repository = new RestaurantsRepository();
    }

    public RestaurantService(final RestaurantsRepository repository) {
        this.repository = repository;
    }

    public RestaurantsDto getById(int i) {
        try {
            Restaurants restaurants = repository.findById(i);
            return mapper.toDto(restaurants);
        } catch (Exception e) {
            logger.severe(ErrorHandler.errorMassage(this.getClass().getName(), e));
            return null;
        }

    }

    public List<RestaurantsDto> getAll() {
        try {
            List<Restaurants> restaurants = repository.findAll();
            return mapper.toDoListEntity(restaurants);
        } catch (Exception e) {
            logger.severe(ErrorHandler.errorMassage(this.getClass().getName(), e));
            return null;
        }

    }

    public RestaurantsDto save(RestaurantsDto dto) {
        try {
            Restaurants restaurants = repository.save(mapper.toEntity(dto));
            return mapper.toDto(restaurants);
        } catch (Exception e) {
            logger.severe(ErrorHandler.errorMassage(this.getClass().getName(), e));
            return null;
        }

    }

    public RestaurantsDto update(RestaurantsDto dto, int id) {
        try {
            Restaurants restaurants = repository.update(mapper.toEntity(dto), id);
            return mapper.toDto(restaurants);
        } catch (Exception e) {
            logger.severe(ErrorHandler.errorMassage(this.getClass().getName(), e));
            return null;
        }

    }

    public boolean delete(int id) {
        try {
            return repository.delete(id);
        } catch (Exception e) {
            logger.severe(ErrorHandler.errorMassage(this.getClass().getName(), e));
            return false;
        }

    }

    public List<RestaurantsDto> getAllRestaurantsByMenuID(int menuID) {
        try {
            List<Restaurants> restaurants = repository.findRestByMenuID(menuID);
            return mapper.toDoListEntity(restaurants);
        } catch (Exception e) {
            logger.severe(ErrorHandler.errorMassage(this.getClass().getName(), e));
            return null;
        }

    }
}
