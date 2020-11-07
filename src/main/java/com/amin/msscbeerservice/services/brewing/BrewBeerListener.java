package com.amin.msscbeerservice.services.brewing;

import com.amin.common.events.BrewBeerEvent;
import com.amin.common.events.NewInventoryEvent;
import com.amin.msscbeerservice.config.JmsConfig;
import com.amin.msscbeerservice.domain.Beer;
import com.amin.msscbeerservice.repositories.BeerRepository;
import com.amin.msscbeerservice.web.model.BeerDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class BrewBeerListener {
    private final BeerRepository beerRepository;
    private final JmsTemplate jmsTemplate;

    @Transactional
    @JmsListener(destination = JmsConfig.BREWING_REQUEST_QUEUE)
    public void listen(BrewBeerEvent brewBeerEvent) {
        BeerDto beerDto = brewBeerEvent.getBeerDto();
        Beer beer = beerRepository.getOne(beerDto.getId());
        beerDto.setQuantityOnHand(beer.getQuantityToBrew());
        NewInventoryEvent newInventoryEvent = new NewInventoryEvent(beerDto);
        log.debug("Brewed beer: " + beer.getMinOnHand() + " : QOH: " + beerDto.getQuantityOnHand());
        jmsTemplate.convertAndSend(JmsConfig.NEW_INVENTORY_QUEUE, newInventoryEvent);
    }
}
