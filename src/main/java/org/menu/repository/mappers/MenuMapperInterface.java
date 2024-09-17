package org.menu.repository.mappers;


import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.menu.model.Menu;
import org.menu.servlet.dto.MenuDto;

import java.util.List;

@Mapper
public interface RestaurantsIntreface {

    MenuMapperInterface INSTANCE  = Mappers.getMapper(MenuMapperInterface.class);
    MenuDto toDto(Menu menu);
    List<MenuDto> toDoList(List<Menu> menus);
}
