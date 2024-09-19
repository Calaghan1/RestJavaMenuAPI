package org.menu.service;

import org.menu.model.Menu;
import org.menu.repository.DishesRepository;
import org.menu.repository.MenuRepository;
import org.menu.repository.mappers.MenuMapperInterface;
import org.menu.servlet.dto.MenuDto;

import java.util.List;
import java.util.logging.Logger;

public class MenuService {
    Logger logger = Logger.getLogger("MenuService");
    private final MenuRepository menuRepo;
    private final DishesRepository dishRepo;
    private final MenuMapperInterface mapper = MenuMapperInterface.INSTANCE;

    public MenuService() {
        this.menuRepo = new MenuRepository();
        this.dishRepo = new DishesRepository();
    }
    public MenuService(MenuRepository menuRepo, DishesRepository dishRepo) {
        this.menuRepo = menuRepo;
        this.dishRepo = dishRepo;
    }
    public MenuDto getById(int i) {
        try {
            Menu menu = menuRepo.findById(i);
            menu.setDishes(dishRepo.findAllByMenuID(i));
            return mapper.toDto(menu);
        } catch (Exception e) {
            logger.severe("Error in " + this.getClass().getName() + ": " + e.getMessage());
            return null;
        }

    }
    public List<MenuDto> getAll() {
        try {
            List<Menu> menus = menuRepo.findAll();
            for (Menu menu : menus) {
                menu.setDishes(dishRepo.findAllByMenuID(menu.getId()));
            }
            return mapper.toDoList(menus);
        } catch (Exception e) {
            logger.severe("Error in " + this.getClass().getName() + ": " + e.getMessage());
            return null;
        }

    }
    public MenuDto save(MenuDto menuDto) {
        try {
            Menu resp = menuRepo.save(mapper.toEntity(menuDto));
            return mapper.toDto(resp);
        } catch (Exception e) {
            logger.severe("Error in " + this.getClass().getName() + ": " + e.getMessage());
            return null;
        }

    }
    public MenuDto update(MenuDto menuDto, int menuID) {
        try {
            Menu resp = menuRepo.update(mapper.toEntity(menuDto), menuID);
            return mapper.toDto(resp);
        } catch (Exception e) {
            logger.severe("Error in " + this.getClass().getName() + ": " + e.getMessage());
            return null;
        }

    }
    public boolean delete(int i) {
        try {
            return menuRepo.delete(i);
        } catch (Exception e) {
            logger.severe("Error in " + this.getClass().getName() + ": " + e.getMessage());
            return false;
        }

    }
    public List<MenuDto> findMenuByRestaurantId(int id) {
        try {
            List<Menu> resp = menuRepo.findMenuByRestaurantId(id);
            return mapper.toDoList(resp);
        } catch (Exception e) {
            logger.severe("Error in " + this.getClass().getName() + ": " + e.getMessage());
            return null;
        }

    }

}
