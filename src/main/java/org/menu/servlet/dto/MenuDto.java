package org.menu.servlet.dto;

import org.menu.model.Dishes;

import java.util.List;

public class MenuDto {
     int id;
     String name;
     String description;
     List<DishesDto> dishes;
     List<RestaurantsDto> restaurants;
     public MenuDto() {}
     public MenuDto(int id, String name, String description, List<DishesDto> dishes) {
          this.id = id;
          this.name = name;
          this.description = description;
          this.dishes = dishes;
     }
     public int getId() {
          return id;
     }
     public void setId(int id) {
          this.id = id;
     }
     public String getName() {
          return name;
     }
     public void setName(String name) {
          this.name = name;
     }
     public String getDescription() {
          return description;
     }
     public void setDescription(String description) {
          this.description = description;
     }
     public List<DishesDto> getDishes() {
          return dishes;
     }
     public void setDishes(List<DishesDto> dishes) {
          this.dishes = dishes;
     }
     public List<RestaurantsDto> getRestaurant_id() {
          return restaurants;
     }
     public void setRestaurant_id(List<RestaurantsDto> restaurant_id) {
          this.restaurants = restaurant_id;
     }
}

