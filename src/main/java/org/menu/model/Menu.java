package org.menu.model;

import java.util.List;


public class Menu {
    private int id;
    private String name;
    private String description;
    private List<Dishes> dishes;
    private List<Restaurants> restaurants;
    public Menu() {}
    public Menu(int id, String name, String description, List<String> submenus, List<Dishes> dishes) {
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

    public List<Dishes> getDishes() {
        return dishes;
    }
    public void setDishes(List<Dishes> dishes) {
        this.dishes = dishes;
    }
    public List<Restaurants> getRestaurants() {
        return restaurants;
    }
    public void setRestaurants(List<Restaurants> restaurants) {
        this.restaurants = restaurants;
    }
    public static String tableName() {
        return "menu";
    }
}
