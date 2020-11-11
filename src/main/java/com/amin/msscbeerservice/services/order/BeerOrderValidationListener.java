package com.amin.msscbeerservice.services.order;

import com.amin.brewery.model.events.ValidateOrderRequest;
import com.amin.brewery.model.events.ValidateOrderResult;
import com.amin.msscbeerservice.config.JmsConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BeerOrderValidationListener {
    private final JmsTemplate jmsTemplate;
    private final BeerOrderValidator beerOrderValidator;

    @JmsListener(destination = JmsConfig.VALIDATE_ORDER_QUEUE)
    public void listen(ValidateOrderRequest validateOrderRequest) {
        Boolean isValid = beerOrderValidator.validateOrder(validateOrderRequest.getBeerOrderDto());
        jmsTemplate.convertAndSend(JmsConfig.VALIDATE_ORDER_RESPONSE_QUEUE, ValidateOrderResult.builder()
                .orderId(validateOrderRequest.getBeerOrderDto().getId()).isValid(isValid).build());
    }
}
