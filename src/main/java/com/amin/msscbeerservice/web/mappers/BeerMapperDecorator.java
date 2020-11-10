package com.amin.msscbeerservice.web.mappers;

import com.amin.brewery.model.BeerDto;
import com.amin.msscbeerservice.domain.Beer;
import com.amin.msscbeerservice.services.inventory.BeerInventoryService;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class BeerMapperDecorator implements BeerMapper {
    @Autowired
    private BeerMapper beerMapper;

    @Autowired
    private BeerInventoryService beerInventoryService;

    @Override
    public BeerDto fromBeerToBeerDto(Beer beer) {
        return beerMapper.fromBeerToBeerDto(beer);
    }

    @Override
    public BeerDto fromBeerToBeerDtoWithInventory(Beer beer) {
        BeerDto beerDto = beerMapper.fromBeerToBeerDto(beer);
        beerDto.setQuantityOnHand(beerInventoryService.getQuantityOnHandInventory(beer.getId()));
        return beerDto;
    }

    @Override
    public Beer fromBeerDtoToBeer(BeerDto beerDto) {
        return beerMapper.fromBeerDtoToBeer(beerDto);
    }
}
