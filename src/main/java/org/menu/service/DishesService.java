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
    private final DishesRepository rep;
    private static final DishesMapperInterface mapper = DishesMapperInterface.INSTANCE;
    public DishesService(){
        rep = new DishesRepository();
    }
    public DishesService(DishesRepository rep){
        this.rep = rep;
    }
    public DishesDto getById(int i) {
        try {
            Dishes resp = rep.findById(i);
            return mapper.toDto(resp);
        } catch (SQLException e) {
            logger.severe(ErrorHandler.errorMassage(this.getClass().getName(), e));
            return null;
        }
    }
    public List<DishesDto> getAll() {
        try {
            List<Dishes> dishes = rep.findAll();
            return mapper.toDoList(dishes);
        } catch (SQLException e) {
            logger.severe(ErrorHandler.errorMassage(this.getClass().getName(), e));
            return null;
        }
    }
    public DishesDto save(DishesDto dishesDto) {
        try {
            Dishes resp = rep.save(mapper.toEntity(dishesDto));
            return mapper.toDto(resp);
        } catch (SQLException e) {
            logger.severe(ErrorHandler.errorMassage(this.getClass().getName(), e));
            return null;
        }
    }
    public DishesDto update(DishesDto dishesDto, int id) {
        try {
            Dishes resp = rep.update(mapper.toEntity(dishesDto), id);
            return mapper.toDto(resp);
        } catch (SQLException e) {
            logger.severe(ErrorHandler.errorMassage(this.getClass().getName(), e));
            return null;
        }

    }
    public boolean delete(int dishID) {
        try {
            return rep.delete(dishID);
        } catch (SQLException e) {
            logger.severe(ErrorHandler.errorMassage(this.getClass().getName(), e));
            return false;
        }
    }
}
