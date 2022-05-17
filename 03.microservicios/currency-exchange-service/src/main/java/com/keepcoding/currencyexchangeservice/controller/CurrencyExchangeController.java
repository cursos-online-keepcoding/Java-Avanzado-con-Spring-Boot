package com.keepcoding.currencyexchangeservice.controller;

import com.keepcoding.currencyexchangeservice.repository.ExchangeValueRepository;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
public class CurrencyExchangeController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CurrencyExchangeController.class);

    @Autowired
    private ExchangeValueRepository exchangeValueRepository;

    @Autowired
    private Environment environment;

    @GetMapping("/currency-exchange/from/{from}/to/{to}")
    public ExchangeValue retrieveExchangeValue(@PathVariable String from, @PathVariable String to) {
        int port = Integer.parseInt(environment.getProperty("local.server.port"));
        ExchangeValue exchangeValue = exchangeValueRepository.findByFromAndTo(from, to);
        LOGGER.info("from : {} - to : {}", from, to);
        exchangeValue.setPort(port);
        return exchangeValue;
    }

    @GetMapping("/hystrix-example")
    @HystrixCommand(fallbackMethod = "fallbackRetrieveExchangeValue")
    public ExchangeValue retrieveExchangeValueFault() {
        throw new RuntimeException("Not available");
    }

    public ExchangeValue fallbackRetrieveExchangeValue() {
        return new ExchangeValue(1l, "PTS", "PTS", 0, BigDecimal.valueOf(1.0));
    }
}
