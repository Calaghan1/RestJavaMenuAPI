package org.menu.repository.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.menu.model.Restaurants;
import org.menu.servlet.dto.RestaurantsDto;

import java.util.List;

@Mapper
public interface RestaurantsInterface {
    RestaurantsInterface INSTANCE  = Mappers.getMapper(RestaurantsInterface.class);
    RestaurantsDto toDto(Restaurants restaurant);
    List<RestaurantsDto> toDoList(List<Restaurants> restaurants);
    Restaurants toEntity(RestaurantsDto restaurantsDto);
    List<RestaurantsDto> toDoListEntity(List<Restaurants> restaurants);
}
