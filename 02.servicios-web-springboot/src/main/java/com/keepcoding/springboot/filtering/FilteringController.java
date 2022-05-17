package com.keepcoding.springboot.filtering;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@RestController
public class FilteringController {


    @GetMapping("/filter")
    public MappingJacksonValue getUserFiltered(@RequestParam String filterParam) {
        Set<String> filters = new HashSet<>(Arrays.asList(filterParam.split(",")));
        UserFilteredDto user = new UserFilteredDto(1, "user1", "1234");

        SimpleBeanPropertyFilter propertyFilter = SimpleBeanPropertyFilter.filterOutAllExcept(filters);
        FilterProvider filter = new SimpleFilterProvider().addFilter("UserFilter", propertyFilter);
        MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(user);
        mappingJacksonValue.setFilters(filter);
        return mappingJacksonValue;
    }
}
