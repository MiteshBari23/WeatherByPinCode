package com.example.task.demo.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class WeatherClient {

    private final RestTemplate restTemplate;

    @Value("${weather.api.key}")
    private String apiKey;

    public WeatherClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public Map<String, Object> getWeather(double lat, double lon){

        String url = "https://api.openweathermap.org/data/2.5/weather?lat="
                + lat + "&lon=" + lon + "&appid=" + apiKey + "&units=metric";

        System.out.println("WEATHER URL: " + url);

        Map<String, Object> response =
                restTemplate.getForObject(url, Map.class);

        System.out.println("Weather API Response: " + response);

        if (response == null || !response.get("cod").toString().equals("200")) {
            throw new RuntimeException("Weather API failed: " + response);
        }

        return response;
    }
}
