package org.menu.servlet.dto;

import java.util.List;

public class RestaurantsDto {
    private int id;
    private String name;
    private List<MenuDto> menuDtos;
    public RestaurantsDto() {}
    public RestaurantsDto(int id, String name, List<MenuDto> menuDtos) {
        this.id = id;
        this.name = name;
        this.menuDtos = menuDtos;
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
    public List<MenuDto> getMenuDtos() {
        return menuDtos;
    }
    public void setMenuDtos(List<MenuDto> menuDtos) {
        this.menuDtos = menuDtos;
    }
}
