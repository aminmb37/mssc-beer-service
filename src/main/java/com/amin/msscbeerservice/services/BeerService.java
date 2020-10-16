package com.amin.msscbeerservice.services;

import com.amin.msscbeerservice.web.model.BeerDto;
import com.amin.msscbeerservice.web.model.BeerPagedList;
import org.springframework.data.domain.PageRequest;

import java.util.UUID;

public interface BeerService {
    BeerPagedList listBeers(String beerName, String beerStyle, Boolean showInventoryOnHand, PageRequest pageRequest);

    BeerDto getBeerById(UUID beerId, Boolean showInventoryOnHand);

    BeerDto saveNewBeer(BeerDto beerDto);

    BeerDto updateBeer(UUID beerId, BeerDto beerDto);
}
