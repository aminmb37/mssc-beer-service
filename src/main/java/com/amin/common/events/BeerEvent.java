package com.amin.common.events;

import com.amin.msscbeerservice.web.model.BeerDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class BeerEvent implements Serializable {
    private static final long serialVersionUID = -9020748034171369854L;

    private BeerDto beerDto;
}
