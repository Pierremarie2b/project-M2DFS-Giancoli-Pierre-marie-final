package com.project.weather.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class CurrentController {

    String query;

    @ApiOperation(value = "Get details for city", response = CurrentController.class, tags = "city")
    @RequestMapping(value = "/city/{query}", method = RequestMethod.GET)
    public String city(@PathVariable String query) {
        String response = restTemplate.exchange("http://dataservice.accuweather.com/locations/v1/cities/search?apikey=mUyJO0xZpBrjorQvu8O9IkKBF1gvIXRJ&q=" + query + "&language=fr-fr",
                HttpMethod.GET, null, new ParameterizedTypeReference<String>() {}, query).getBody();
        return response;
    }

    @Autowired
    RestTemplate restTemplate;

    @ApiOperation(value = "Get details for weather", response = CurrentController.class, tags = "meteo")
    @RequestMapping(value = "/meteo/{query}", method = RequestMethod.GET)
    public String meteo(@PathVariable String query) {
        String response = restTemplate.exchange("http://dataservice.accuweather.com/currentconditions/v1/{key}?apikey=mUyJO0xZpBrjorQvu8O9IkKBF1gvIXRJ&language=fr-fr",
                HttpMethod.GET, null, new ParameterizedTypeReference<String>() {}, query).getBody();
        return response;
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
