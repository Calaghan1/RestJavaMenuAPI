package org.menu.repository.mappers;


import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.menu.model.Dishes;
import org.menu.servlet.dto.DishesDto;

import java.util.List;

@Mapper
public interface DishesMapperInterface {

    DishesMapperInterface INSTANCE  = Mappers.getMapper(DishesMapperInterface.class);
    DishesDto toDto(Dishes dishes);
    List<DishesDto> toDoList(List<Dishes> dishes);
    Dishes toEntity(DishesDto dto);
    List<DishesDto> toDoListEntity(List<Dishes> dishes);
}
