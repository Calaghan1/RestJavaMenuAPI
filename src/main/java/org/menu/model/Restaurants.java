package org.menu.model;

import java.util.List;


public class Restaurants {
    private int id;
    private String name;
    private List<Menu> menus;

    public Restaurants() {
    }

    public Restaurants(int id, String name, List<Menu> menus) {
        this.id = id;
        this.name = name;
        this.menus = menus;
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

    public List<Menu> getMenus() {
        return menus;
    }

    public void setMenus(List<Menu> menus) {
        this.menus = menus;
    }

    public static String tableName() {
        return "restaurants";
    }
}
