package org.menu.servlet.dto;


public class DishesDto {
    private int id;
    private String name;
    private String description;
    private int menuId;
    public DishesDto() {}
    public DishesDto(int id, String name, String description, int menuId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.menuId = menuId;
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
    public int getMenuId() {
        return menuId;
    }
    public void setMenuId(int menuId) {
        this.menuId = menuId;
    }

}
