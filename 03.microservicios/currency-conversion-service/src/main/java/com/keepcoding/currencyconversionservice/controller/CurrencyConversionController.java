package com.keepcoding.currencyconversionservice.controller;

import com.keepcoding.currencyconversionservice.CurrencyExchangeServiceProxy;
import com.keepcoding.currencyconversionservice.model.CurrencyConversionBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
public class CurrencyConversionController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CurrencyConversionController.class);
    @Autowired
    CurrencyExchangeServiceProxy currencyExchangeServiceProxy;

    @GetMapping("/currency-converter/from/{from}/to/{to}/{quantity}")
    public CurrencyConversionBean convertCurrency(@PathVariable("from") String from, @PathVariable("to") String to, @PathVariable BigDecimal quantity) {

        CurrencyConversionBean result = currencyExchangeServiceProxy.retrieveExchangeValue(from, to);
        LOGGER.info("from : {} - to : {}", result.getFrom(), result.getTo());
        return new CurrencyConversionBean(result.getId(), result.getFrom(), result.getTo(),
                result.getConversionMultiple(), quantity, quantity.multiply(result.getConversionMultiple()), result.getPort());
    }
}
