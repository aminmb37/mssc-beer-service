package com.amin.msscbeerservice.services;

import com.amin.msscbeerservice.domain.Beer;
import com.amin.msscbeerservice.repositories.BeerRepository;
import com.amin.msscbeerservice.web.mappers.BeerMapper;
import com.amin.msscbeerservice.web.model.BeerDto;
import com.amin.msscbeerservice.web.model.BeerPagedList;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.persistence.EntityNotFoundException;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BeerServiceImpl implements BeerService {
    private final BeerMapper beerMapper;

    private final BeerRepository beerRepository;

    @Override
    public BeerPagedList listBeers(String beerName, String beerStyle,
                                   Boolean showInventoryOnHand, PageRequest pageRequest) {
        Page<Beer> beerPage;
        if (StringUtils.isEmpty(beerName) && StringUtils.isEmpty(beerStyle)) {
            beerPage = beerRepository.findAll(pageRequest);
        } else if (!StringUtils.isEmpty(beerName) && StringUtils.isEmpty(beerStyle)) {
            beerPage = beerRepository.findAllByBeerName(beerName, pageRequest);
        } else if (StringUtils.isEmpty(beerName) && !StringUtils.isEmpty(beerStyle)) {
            beerPage = beerRepository.findAllByBeerStyle(beerStyle, pageRequest);
        } else {
            beerPage = beerRepository.findAllByBeerNameAndBeerStyle(beerName, beerStyle, pageRequest);
        }
        if (showInventoryOnHand) {
            return new BeerPagedList(beerPage.getContent().stream()
                    .map(beerMapper::fromBeerToBeerDtoWithInventory).collect(Collectors.toList()),
                    PageRequest.of(beerPage.getPageable().getPageNumber(), beerPage.getPageable().getPageSize()),
                    beerPage.getTotalElements());
        } else {
            return new BeerPagedList(beerPage.getContent().stream()
                    .map(beerMapper::fromBeerToBeerDto).collect(Collectors.toList()),
                    PageRequest.of(beerPage.getPageable().getPageNumber(), beerPage.getPageable().getPageSize()),
                    beerPage.getTotalElements());
        }
    }

    @Override
    public BeerDto getBeerById(UUID beerId, Boolean showInventoryOnHand) {
        if (showInventoryOnHand) {
            return beerMapper.fromBeerToBeerDtoWithInventory(beerRepository
                    .findById(beerId).orElseThrow(EntityNotFoundException::new));
        } else {
            return beerMapper.fromBeerToBeerDto(beerRepository
                    .findById(beerId).orElseThrow(EntityNotFoundException::new));
        }
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
