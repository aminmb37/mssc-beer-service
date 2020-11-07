package com.amin.msscbeerservice.web.mappers;

import com.amin.msscbeerservice.domain.Beer;
import com.amin.msscbeerservice.web.model.BeerDto;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;

@Mapper(uses = DateMapper.class)
@DecoratedWith(BeerMapperDecorator.class)
public interface BeerMapper {
    BeerDto fromBeerToBeerDto(Beer beer);

    BeerDto fromBeerToBeerDtoWithInventory(Beer beer);

    Beer fromBeerDtoToBeer(BeerDto beerDto);
}
