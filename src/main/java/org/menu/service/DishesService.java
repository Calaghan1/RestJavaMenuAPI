package org.menu.service;

import org.menu.model.Dishes;
import org.menu.repository.DishesRepository;
import org.menu.repository.mappers.DishesMapperInterface;
import org.menu.servlet.dto.DishesDto;


import java.sql.SQLException;
import java.util.List;
import java.util.logging.Logger;

public class DishesService {
    Logger logger = Logger.getLogger(DishesService.class.getName());
    private final DishesRepository repository;
    private static final DishesMapperInterface mapper = DishesMapperInterface.INSTANCE;

    public DishesService() {
        repository = new DishesRepository();
    }

    public DishesService(DishesRepository repository) {
        this.repository = repository;
    }

    public DishesDto getById(int i) {
        try {
            Dishes dishes = repository.findById(i);
            return mapper.toDto(dishes);
        } catch (SQLException e) {
            logger.severe(ErrorHandler.errorMassage(this.getClass().getName(), e));
            return null;
        }
    }

    public List<DishesDto> getAll() {
        try {
            List<Dishes> dishes = repository.findAll();
            return mapper.toDoList(dishes);
        } catch (SQLException e) {
            logger.severe(ErrorHandler.errorMassage(this.getClass().getName(), e));
            return null;
        }
    }

    public DishesDto save(DishesDto dishesDto) {
        try {
            Dishes dishes = repository.save(mapper.toEntity(dishesDto));
            return mapper.toDto(dishes);
        } catch (SQLException e) {
            logger.severe(ErrorHandler.errorMassage(this.getClass().getName(), e));
            return null;
        }
    }

    public DishesDto update(DishesDto dishesDto, int id) {
        try {
            Dishes dishes = repository.update(mapper.toEntity(dishesDto), id);
            return mapper.toDto(dishes);
        } catch (SQLException e) {
            logger.severe(ErrorHandler.errorMassage(this.getClass().getName(), e));
            return null;
        }

    }

    public boolean delete(int dishID) {
        try {
            return repository.delete(dishID);
        } catch (SQLException e) {
            logger.severe(ErrorHandler.errorMassage(this.getClass().getName(), e));
            return false;
        }
    }
}
