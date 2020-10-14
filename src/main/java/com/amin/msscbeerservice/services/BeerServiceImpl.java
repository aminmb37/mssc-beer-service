package com.amin.msscbeerservice.services;

import com.amin.msscbeerservice.domain.Beer;
import com.amin.msscbeerservice.repositories.BeerRepository;
import com.amin.msscbeerservice.web.mappers.BeerMapper;
import com.amin.msscbeerservice.web.model.BeerDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BeerServiceImpl implements BeerService {
    private final BeerRepository beerRepository;
    private final BeerMapper beerMapper;

    @Override
    public BeerDto getBeerById(UUID beerId) {
        return beerMapper.fromBeerToBeerDto(beerRepository.findById(beerId).orElseThrow(EntityNotFoundException::new));
    }

    @Override
    public BeerDto saveNewBeer(BeerDto beerDto) {
        return beerMapper.fromBeerToBeerDto(beerRepository.save(beerMapper.fromBeerDtoToBeer(beerDto)));
    }

    @Override
    public BeerDto updateBeer(UUID beerId, BeerDto beerDto) {
        Beer beer = beerRepository.findById(beerId).orElseThrow(EntityNotFoundException::new);

        beer.setBeerName(beerDto.getBeerName());
        beer.setBeerStyle(beerDto.getBeerStyle().name());
        beer.setPrice(beerDto.getPrice());
        beer.setUpc(beerDto.getUpc());

        return beerMapper.fromBeerToBeerDto(beerRepository.save(beer));
    }
}
