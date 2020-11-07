package com.amin.msscbeerservice.services;

import com.amin.msscbeerservice.config.JmsConfig;
import com.amin.msscbeerservice.domain.Beer;
import com.amin.msscbeerservice.events.BrewBeerEvent;
import com.amin.msscbeerservice.repositories.BeerRepository;
import com.amin.msscbeerservice.services.inventory.BeerInventoryService;
import com.amin.msscbeerservice.web.mappers.BeerMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class BrewingService {
    private final BeerRepository beerRepository;
    private final BeerInventoryService beerInventoryService;
    private final JmsTemplate jmsTemplate;
    private final BeerMapper beerMapper;

    @Scheduled(fixedRate = 5000) // every 5 seconds
    public void checkForLowInventory() {
        List<Beer> beers = beerRepository.findAll();
        beers.forEach(beer -> {
            Integer invQOH = beerInventoryService.getQuantityOnHandInventory(beer.getId());
            log.debug("Min on hand is: " + beer.getMinOnHand());
            log.debug("Inventory is: " + invQOH);
            if (invQOH <= beer.getMinOnHand()) {
                jmsTemplate.convertAndSend(JmsConfig.BREWING_REQUEST_QUEUE,
                        new BrewBeerEvent(beerMapper.fromBeerToBeerDto(beer)));
            }
        });
    }
}
