package com.amin.msscbeerservice.services;

import com.amin.brewery.model.BeerDto;
import com.amin.brewery.model.BeerPagedList;
import org.springframework.data.domain.PageRequest;

import java.util.UUID;

public interface BeerService {
    BeerPagedList listBeers(String beerName, String beerStyle, Boolean showInventoryOnHand, PageRequest pageRequest);

    BeerDto getBeerById(UUID beerId, Boolean showInventoryOnHand);

    BeerDto saveNewBeer(BeerDto beerDto);

    BeerDto updateBeer(UUID beerId, BeerDto beerDto);

    BeerDto getBeerByUpc(String beerUpc);
}
