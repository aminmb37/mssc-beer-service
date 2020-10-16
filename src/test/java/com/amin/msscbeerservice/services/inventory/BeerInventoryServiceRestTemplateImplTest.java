package com.amin.msscbeerservice.services.inventory;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

@Disabled
@SpringBootTest
public class BeerInventoryServiceRestTemplateImplTest {
    @Autowired
    private BeerInventoryService beerInventoryService;

    @BeforeEach
    public void setup() {
    }

    @Test
    public void getQuantityOnHandInventory() {
        beerInventoryService.getQuantityOnHandInventory(UUID.fromString("0a818933-087d-47f2-ad83-2f986ed087eb"));
    }
}
