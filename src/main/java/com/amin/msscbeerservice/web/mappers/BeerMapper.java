package com.amin.msscbeerservice.web.mappers;

import com.amin.brewery.model.BeerDto;
import com.amin.msscbeerservice.domain.Beer;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;

@Mapper(uses = DateMapper.class)
@DecoratedWith(BeerMapperDecorator.class)
public interface BeerMapper {
    BeerDto fromBeerToBeerDto(Beer beer);

    BeerDto fromBeerToBeerDtoWithInventory(Beer beer);

    Beer fromBeerDtoToBeer(BeerDto beerDto);
}
