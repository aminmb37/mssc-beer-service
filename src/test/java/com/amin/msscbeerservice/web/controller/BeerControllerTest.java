package com.amin.msscbeerservice.web.controller;

import com.amin.msscbeerservice.bootstrap.BeerLoader;
import com.amin.msscbeerservice.services.BeerService;
import com.amin.msscbeerservice.services.inventory.BeerInventoryService;
import com.amin.msscbeerservice.web.model.BeerDto;
import com.amin.msscbeerservice.web.model.BeerStyleEnum;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.constraints.ConstraintDescriptions;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.restdocs.snippet.Attributes.key;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(RestDocumentationExtension.class)
@AutoConfigureRestDocs//(uriScheme = "https", uriHost = "dev.springframework.guru", uriPort = 80)
@WebMvcTest(BeerController.class)
@ComponentScan(basePackages = "com.amin.msscbeerservice.web.mappers")
class BeerControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private BeerService beerService;

    @MockBean
    private BeerInventoryService beerInventoryService;

    @Test
    void getBeerById() throws Exception {
        given(beerService.getBeerById(any())).willReturn(getValidBeerDto());

        ConstrainedFields fields = new ConstrainedFields(BeerDto.class);

        mockMvc.perform(get("/api/v1/beer/{beerId}", UUID.randomUUID())
                //.param("iscold", "yes")
                .accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
                .andDo(document("v1/beer-get",
                        pathParameters(parameterWithName("beerId").description("UUID of desired beer to get.")),
                        //requestParameters(parameterWithName("iscold").description("Is Beer Cold Query Parameter")),
                        responseFields(
                                fields.withPath("id")
                                        .description("Identifier of Beer").type(UUID.class.getSimpleName()),
                                fields.withPath("version")
                                        .description("Version Number").type(Integer.class.getSimpleName()),
                                fields.withPath("createdDate")
                                        .description("Date Created").type(OffsetDateTime.class.getSimpleName()),
                                fields.withPath("lastModifiedDate")
                                        .description("Last Updated Date").type(OffsetDateTime.class.getSimpleName()),
                                fields.withPath("beerName")
                                        .description("Beer Name").type(String.class.getSimpleName()),
                                fields.withPath("beerStyle")
                                        .description("Beer Style").type(BeerStyleEnum.class.getSimpleName()),
                                fields.withPath("upc").
                                        description("UPC of Beer").type(Long.class.getSimpleName()),
                                fields.withPath("price")
                                        .description("Price of Beer").type(BigDecimal.class.getSimpleName()),
                                fields.withPath("quantityOnHand")
                                        .description("Quantity On Hand").type(Integer.class.getSimpleName())
                        )));
    }

    @Test
    void saveNewBeer() throws Exception {
        BeerDto beerDto = getValidBeerDto();
        String beerDtoJson = objectMapper.writeValueAsString(beerDto);

        given(beerService.saveNewBeer(any())).willReturn(getValidBeerDto());

        ConstrainedFields fields = new ConstrainedFields(BeerDto.class);

        mockMvc.perform(post("/api/v1/beer/").contentType(MediaType.APPLICATION_JSON)
                .content(beerDtoJson)).andExpect(status().isCreated())
                .andDo(document("v1/beer-save", requestFields(
                        fields.withPath("id").ignored(),
                        fields.withPath("version").ignored(),
                        fields.withPath("createdDate").ignored(),
                        fields.withPath("lastModifiedDate").ignored(),
                        fields.withPath("beerName").description("Name of the Beer"),
                        fields.withPath("beerStyle").description("Style of the Beer"),
                        fields.withPath("upc").description("Beer UPC"),
                        fields.withPath("price").description("Price of the Beer"),
                        fields.withPath("quantityOnHand").ignored()
                )));
    }

    @Test
    void updateBeerById() throws Exception {
        BeerDto beerDto = getValidBeerDto();
        String beerDtoJson = objectMapper.writeValueAsString(beerDto);

        given(beerService.updateBeer(any(), any())).willReturn(getValidBeerDto());

        ConstrainedFields fields = new ConstrainedFields(BeerDto.class);

        mockMvc.perform(put("/api/v1/beer/{beerId}", UUID.randomUUID())
                .contentType(MediaType.APPLICATION_JSON).content(beerDtoJson))
                .andExpect(status().isNoContent())
                .andDo(document("v1/beer-update",
                        pathParameters(parameterWithName("beerId").description("UUID of desired beer to update.")),
                        requestFields(
                                fields.withPath("id").ignored(),
                                fields.withPath("version").ignored(),
                                fields.withPath("createdDate").ignored(),
                                fields.withPath("lastModifiedDate").ignored(),
                                fields.withPath("beerName").description("Name of the Beer"),
                                fields.withPath("beerStyle").description("Style of the Beer"),
                                fields.withPath("upc").description("Beer UPC"),
                                fields.withPath("price").description("Price of the Beer"),
                                fields.withPath("quantityOnHand").ignored()
                        )));
    }

    BeerDto getValidBeerDto() {
        return BeerDto.builder()
                .beerName("My Beer")
                .beerStyle(BeerStyleEnum.ALE)
                .price(new BigDecimal("2.99"))
                .upc(BeerLoader.BEER_1_UPC)
                .build();
    }

    private static class ConstrainedFields {
        private final ConstraintDescriptions constraintDescriptions;

        private ConstrainedFields(Class<?> input) {
            constraintDescriptions = new ConstraintDescriptions(input);
        }

        private FieldDescriptor withPath(String path) {
            return fieldWithPath(path).attributes(key("constraints").value(StringUtils.
                    collectionToDelimitedString(constraintDescriptions.descriptionsForProperty(path), ". ")));
        }
    }
}
