package com.amin.msscbeerservice.services.inventory;

import com.amin.msscbeerservice.services.inventory.model.BeerInventoryDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Profile;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Component
@Slf4j
@Profile("!local-discovery")
@ConfigurationProperties(prefix = "amin.brewery", ignoreUnknownFields = true)
public class BeerInventoryServiceRestTemplateImpl implements BeerInventoryService {
    public static final String INVENTORY_PATH = "api/v1/beer/{beerId}/inventory";

    private String beerInventoryServiceHost;

    private final RestTemplate restTemplate;

    public BeerInventoryServiceRestTemplateImpl(RestTemplateBuilder restTemplateBuilder,
                                                @Value("${amin.brewery.inventory-user}") String inventoryUser,
                                                @Value("${amin.brewery.inventory-password}") String inventoryPassword) {
        this.restTemplate = restTemplateBuilder.basicAuthentication(inventoryUser, inventoryPassword).build();
    }

    public void setBeerInventoryServiceHost(String beerInventoryServiceHost) {
        this.beerInventoryServiceHost = beerInventoryServiceHost;
    }

    @Override
    public Integer getQuantityOnHandInventory(UUID beerId) {
        log.debug("Calling Inventory Service");
        ResponseEntity<List<BeerInventoryDto>> responseEntity = restTemplate.exchange(
                beerInventoryServiceHost + INVENTORY_PATH, HttpMethod.GET, null,
                new ParameterizedTypeReference<>() {
                }, beerId);
        return Objects.requireNonNull(responseEntity.getBody())
                .stream().mapToInt(BeerInventoryDto::getQuantityOnHand).sum();
    }
}
