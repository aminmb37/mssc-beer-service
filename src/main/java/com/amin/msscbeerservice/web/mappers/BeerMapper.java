package com.amin.msscbeerservice.web.mappers;

import com.amin.msscbeerservice.domain.Beer;
import com.amin.msscbeerservice.web.model.BeerDto;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(uses = DateMapper.class)
@DecoratedWith(BeerMapperDecorator.class)
public interface BeerMapper {
    @Mapping(source = "quantityToBrew", target = "quantityOnHand")
    BeerDto fromBeerToBeerDto(Beer beer);

    @Mapping(target = "minOnHand", ignore = true)
    @Mapping(source = "quantityOnHand", target = "quantityToBrew")
    Beer fromBeerDtoToBeer(BeerDto beerDto);
}
