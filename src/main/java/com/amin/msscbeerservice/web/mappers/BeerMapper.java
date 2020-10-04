package com.amin.msscbeerservice.web.mappers;

import com.amin.msscbeerservice.domain.Beer;
import com.amin.msscbeerservice.web.model.BeerDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(uses = DateMapper.class)
public interface BeerMapper {
    @Mapping(target = "quantityOnHand", source = "quantityToBrew")
    BeerDto fromBeerToBeerDto(Beer beer);

    @Mapping(target = "minOnHand", ignore = true)
    @Mapping(target = "quantityToBrew", source = "quantityOnHand")
    Beer fromBeerDtoToBeer(BeerDto beerDto);
}
