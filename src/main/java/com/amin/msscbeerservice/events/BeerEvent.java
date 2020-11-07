package com.amin.msscbeerservice.events;

import com.amin.msscbeerservice.web.model.BeerDto;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;

@RequiredArgsConstructor
@Data
@Builder
public class BeerEvent implements Serializable {
    private static final long serialVersionUID = -9020748034171369854L;

    private final BeerDto beerDto;
}