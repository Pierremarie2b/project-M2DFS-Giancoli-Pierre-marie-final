package com.project.weather.controller;

import com.google.gson.Gson;
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

import java.util.Map;

@RestController
public class CurrentController {

    String query;
    // Ville
    @ApiOperation(value = "city", response = CurrentController.class, tags = "city")
    @RequestMapping(value = "/city/{query}", method = RequestMethod.GET)
    public String city(@PathVariable String query) {
        String response = restTemplate.exchange("http://dataservice.accuweather.com/locations/v1/cities/search?apikey=4bsKH4V9molKn09LXmHX5CtzbP6TsZUJ&q=" + query + "&language=fr-fr",
                HttpMethod.GET, null, new ParameterizedTypeReference<String>() {}, query).getBody();
        return response;
    }

    @Autowired
    RestTemplate restTemplate;

    // METEO
    @ApiOperation(value = "weather meteo", response = CurrentController.class, tags = "meteo")
    @RequestMapping(value = "/meteo/{query}", method = RequestMethod.GET)
    public String meteo(@PathVariable String query) {
        String response = restTemplate.exchange("http://dataservice.accuweather.com/currentconditions/v1/{key}?apikey=4bsKH4V9molKn09LXmHX5CtzbP6TsZUJ&language=fr-fr",
                HttpMethod.GET, null, new ParameterizedTypeReference<String>() {}, query).getBody();
        return response;
    }
    // FORECAST DE 1 jour avec l'id
    @ApiOperation(value = "forcast", response = CurrentController.class, tags = "forecast")
    @RequestMapping(value = "/prevu/{query}", method = RequestMethod.GET)
    public String forecast(@PathVariable String query) {
        String response = restTemplate.exchange("http://dataservice.accuweather.com/forecasts/v1/daily/1day//{key}?apikey=4bsKH4V9molKn09LXmHX5CtzbP6TsZUJ&language=fr-fr",
                HttpMethod.GET, null, new ParameterizedTypeReference<String>() {}, query).getBody();
        return response;
    }
    // VILLE / METEO
    @ApiOperation(value = "weather / city", response = CurrentController.class, tags = "meteoCity")
    @RequestMapping(value = "/meteoCity/{query}", method = RequestMethod.GET)
    public String meteoCity(@PathVariable String query) {
        String response = restTemplate.exchange("http://dataservice.accuweather.com/locations/v1/cities/search?apikey=4bsKH4V9molKn09LXmHX5CtzbP6TsZUJ&q=" + query + "&language=fr-fr",
                HttpMethod.GET, null, new ParameterizedTypeReference<String>() {}, query).getBody();

        String json = response;
        json = json.substring(1, json.length() - 1);
        Map jsonJavaRootObject = new Gson().fromJson(json, Map.class);

        String result = (String) jsonJavaRootObject.get("Key");

        String r = restTemplate.exchange("http://dataservice.accuweather.com/currentconditions/v1/" + result + "?apikey=4bsKH4V9molKn09LXmHX5CtzbP6TsZUJ&language=fr-fr",
                HttpMethod.GET, null, new ParameterizedTypeReference<String>() {}, result).getBody();

        return r;
    }
    // FORECATS DE 5 JOUR
    @ApiOperation(value = "5 jour forecats", response = CurrentController.class, tags = "5 jour forecats")
    @RequestMapping(value = "5forecats/{query}", method = RequestMethod.GET)
    public String forecast5jours(@PathVariable String query) {
        String response = restTemplate.exchange("http://dataservice.accuweather.com/locations/v1/cities/search?apikey=4bsKH4V9molKn09LXmHX5CtzbP6TsZUJ&q=" + query + "&language=fr-fr",
                HttpMethod.GET, null, new ParameterizedTypeReference<String>() {}, query).getBody();

        String json = response;
        json = json.substring(1, json.length() - 1);
        Map jsonJavaRootObject = new Gson().fromJson(json, Map.class);

        String result = (String) jsonJavaRootObject.get("Key");

        String r = restTemplate.exchange("http://dataservice.accuweather.com/forecasts/v1/daily/5day/" + result + "?apikey=4bsKH4V9molKn09LXmHX5CtzbP6TsZUJ&language=fr-fr",
                HttpMethod.GET, null, new ParameterizedTypeReference<String>() {}, result).getBody();

        return r;
    }



    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
