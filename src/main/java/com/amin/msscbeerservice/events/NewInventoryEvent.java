package com.amin.msscbeerservice.events;

import com.amin.msscbeerservice.web.model.BeerDto;

public class NewInventoryEvent extends BeerEvent {
    NewInventoryEvent(BeerDto beerDto) {
        super(beerDto);
    }
}
