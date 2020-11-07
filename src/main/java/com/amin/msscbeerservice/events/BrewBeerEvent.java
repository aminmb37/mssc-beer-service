package com.amin.msscbeerservice.events;

import com.amin.msscbeerservice.web.model.BeerDto;

public class BrewBeerEvent extends BeerEvent {
    BrewBeerEvent(BeerDto beerDto) {
        super(beerDto);
    }
}
