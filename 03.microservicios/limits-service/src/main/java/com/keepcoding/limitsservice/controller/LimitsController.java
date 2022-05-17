package com.keepcoding.limitsservice.controller;

import com.keepcoding.limitsservice.config.LimitsProperties;
import com.keepcoding.limitsservice.model.Limits;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LimitsController {

    @Autowired
    LimitsProperties limitsProperties;

    @GetMapping("/limits")
    public Limits getLimits() {
        return new Limits(limitsProperties.getMaximum(), limitsProperties.getMinimum());
    }

}
